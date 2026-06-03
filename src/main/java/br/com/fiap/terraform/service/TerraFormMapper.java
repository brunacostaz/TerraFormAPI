package br.com.fiap.terraform.service;

import br.com.fiap.terraform.dto.AirResponse;
import br.com.fiap.terraform.dto.GreenhouseDashboardResponse;
import br.com.fiap.terraform.dto.GreenhouseResponse;
import br.com.fiap.terraform.dto.InventoryItemResponse;
import br.com.fiap.terraform.dto.OperationLogResponse;
import br.com.fiap.terraform.dto.PlanetResponse;
import br.com.fiap.terraform.dto.PlantResponse;
import br.com.fiap.terraform.dto.SoilResponse;
import br.com.fiap.terraform.entity.AirState;
import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.entity.InventoryItem;
import br.com.fiap.terraform.entity.OperationLog;
import br.com.fiap.terraform.entity.Planet;
import br.com.fiap.terraform.entity.PlantState;
import br.com.fiap.terraform.entity.SoilState;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TerraFormMapper {

    private final GravityService gravityService;

    public TerraFormMapper(GravityService gravityService) {
        this.gravityService = gravityService;
    }

    public PlanetResponse toPlanetResponse(Planet planet) {
        return new PlanetResponse(
                planet.getId(),
                planet.getCode(),
                planet.getName(),
                planet.getGravity(),
                gravityService.calculateGravityFactor(planet.getGravity()),
                planet.getColor()
        );
    }

    public GreenhouseResponse toGreenhouseResponse(Greenhouse greenhouse) {
        return new GreenhouseResponse(
                greenhouse.getId(),
                greenhouse.getName(),
                greenhouse.getStatus(),
                toPlanetResponse(greenhouse.getPlanet()),
                toPlantResponse(greenhouse.getPlant())
        );
    }

    public GreenhouseDashboardResponse toDashboardResponse(Greenhouse greenhouse, List<OperationLog> logs) {
        return new GreenhouseDashboardResponse(
                greenhouse.getId(),
                greenhouse.getName(),
                greenhouse.getStatus(),
                toPlanetResponse(greenhouse.getPlanet()),
                toPlantResponse(greenhouse.getPlant()),
                toSoilResponse(greenhouse.getSoil()),
                toAirResponse(greenhouse.getAir()),
                greenhouse.getInventory().stream().map(this::toInventoryItemResponse).toList(),
                logs.stream().map(this::toOperationLogResponse).toList()
        );
    }

    public SoilResponse toSoilResponse(SoilState soil) {
        return new SoilResponse(
                soil.getPh(),
                soil.getHumidity(),
                soil.getQuality(),
                soil.getNitrogen(),
                soil.getPhosphorus(),
                soil.getPotassium(),
                soil.getCalcium(),
                soil.getMagnesium(),
                soil.getSulfur()
        );
    }

    public AirResponse toAirResponse(AirState air) {
        return new AirResponse(
                air.getOxygen(),
                air.getCarbonDioxide(),
                air.getHumidity(),
                air.getQuality()
        );
    }

    public PlantResponse toPlantResponse(PlantState plant) {
        return new PlantResponse(plant.getSpecies(), plant.getPhase(), plant.getPhaseProgress());
    }

    public InventoryItemResponse toInventoryItemResponse(InventoryItem item) {
        return new InventoryItemResponse(item.getResourceCode(), item.getResourceType(), item.getPercentage());
    }

    public OperationLogResponse toOperationLogResponse(OperationLog log) {
        return new OperationLogResponse(
                log.getId(),
                log.getGreenhouse().getId(),
                log.getGreenhouse().getName(),
                log.getGreenhouse().getPlanet().getCode(),
                log.getType(),
                log.getAlertLevel(),
                log.getDescription(),
                log.getCreatedAt()
        );
    }
}

