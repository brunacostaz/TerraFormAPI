package br.com.fiap.terraform.service;

import br.com.fiap.terraform.dto.GreenhouseDashboardResponse;
import br.com.fiap.terraform.dto.GreenhouseRequest;
import br.com.fiap.terraform.dto.GreenhouseResponse;
import br.com.fiap.terraform.entity.AirState;
import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.entity.OperationLog;
import br.com.fiap.terraform.entity.Planet;
import br.com.fiap.terraform.entity.PlantState;
import br.com.fiap.terraform.entity.SoilState;
import br.com.fiap.terraform.enums.LogType;
import br.com.fiap.terraform.exception.ResourceNotFoundException;
import br.com.fiap.terraform.repository.GreenhouseRepository;
import br.com.fiap.terraform.repository.OperationLogRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GreenhouseService {

    private final GreenhouseRepository greenhouseRepository;
    private final OperationLogRepository operationLogRepository;
    private final PlanetService planetService;
    private final TerraFormMapper mapper;

    public GreenhouseService(GreenhouseRepository greenhouseRepository,
            OperationLogRepository operationLogRepository,
            PlanetService planetService,
            TerraFormMapper mapper) {
        this.greenhouseRepository = greenhouseRepository;
        this.operationLogRepository = operationLogRepository;
        this.planetService = planetService;
        this.mapper = mapper;
    }

    public List<GreenhouseResponse> findAll(String planetCode) {
        List<Greenhouse> greenhouses = planetCode == null || planetCode.isBlank()
                ? greenhouseRepository.findAll()
                : greenhouseRepository.findByPlanetCode(planetCode);

        return greenhouses.stream().map(mapper::toGreenhouseResponse).toList();
    }

    public GreenhouseResponse findById(Long id) {
        return mapper.toGreenhouseResponse(findEntityById(id));
    }

    @Transactional
    public GreenhouseDashboardResponse getDashboard(Long id) {
        Greenhouse greenhouse = findEntityById(id);
        List<OperationLog> logs = operationLogRepository.findTop100ByGreenhouseIdOrderByCreatedAtDesc(id);
        return mapper.toDashboardResponse(greenhouse, logs);
    }

    @Transactional
    public GreenhouseResponse create(GreenhouseRequest request) {
        Planet planet = planetService.findEntityByCode(request.getPlanetCode());
        Greenhouse greenhouse = new Greenhouse(
                request.getName(),
                request.getStatus(),
                planet,
                createSoil(request),
                createAir(request),
                new PlantState(request.getPlantSpecies(), request.getPlantPhase(), request.getPlantPhaseProgress())
        );

        Greenhouse saved = greenhouseRepository.save(greenhouse);
        operationLogRepository.save(new OperationLog(saved, LogType.READING, null, "Estufa criada no backend."));
        return mapper.toGreenhouseResponse(saved);
    }

    @Transactional
    public GreenhouseResponse update(Long id, GreenhouseRequest request) {
        Greenhouse greenhouse = findEntityById(id);
        Planet planet = planetService.findEntityByCode(request.getPlanetCode());

        greenhouse.rename(request.getName());
        greenhouse.changeStatus(request.getStatus());
        greenhouse.moveToPlanet(planet);
        greenhouse.replaceOperationalState(
                createSoil(request),
                createAir(request),
                new PlantState(request.getPlantSpecies(), request.getPlantPhase(), request.getPlantPhaseProgress())
        );

        operationLogRepository.save(new OperationLog(greenhouse, LogType.READING, null, "Estufa atualizada."));
        return mapper.toGreenhouseResponse(greenhouse);
    }

    @Transactional
    public void delete(Long id) {
        Greenhouse greenhouse = findEntityById(id);
        operationLogRepository.deleteByGreenhouseId(id);
        greenhouseRepository.delete(greenhouse);
    }

    public Greenhouse findEntityById(Long id) {
        return greenhouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estufa nao encontrada: " + id));
    }

    private SoilState createSoil(GreenhouseRequest request) {
        return new SoilState(
                request.getSoilPh(),
                request.getSoilHumidity(),
                BigDecimal.valueOf(85),
                BigDecimal.valueOf(70),
                BigDecimal.valueOf(65),
                BigDecimal.valueOf(70),
                BigDecimal.valueOf(60),
                BigDecimal.valueOf(55),
                BigDecimal.valueOf(58)
        );
    }

    private AirState createAir(GreenhouseRequest request) {
        return new AirState(
                request.getAirOxygen(),
                request.getAirCarbonDioxide(),
                request.getAirHumidity(),
                BigDecimal.valueOf(90)
        );
    }
}
