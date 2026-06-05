package br.com.fiap.terraform.service;

import br.com.fiap.terraform.entity.AirState;
import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.entity.PlantState;
import br.com.fiap.terraform.entity.SoilState;
import br.com.fiap.terraform.enums.GreenhouseStatus;
import br.com.fiap.terraform.enums.GrowthPhase;
import br.com.fiap.terraform.enums.OperationalStatus;
import br.com.fiap.terraform.enums.PlantHealthStatus;
import br.com.fiap.terraform.repository.GreenhouseRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SimulationService {

    private static final List<GrowthPhase> PHASES = List.of(
            GrowthPhase.GERMINATION,
            GrowthPhase.SEEDLING,
            GrowthPhase.VEGETATIVE,
            GrowthPhase.FLOWERING,
            GrowthPhase.HARVEST
    );

    private final GreenhouseRepository greenhouseRepository;
    private final GravityService gravityService;
    private final AgricultureCalculationService calculationService;
    private final AlertService alertService;

    public SimulationService(GreenhouseRepository greenhouseRepository,
            GravityService gravityService,
            AgricultureCalculationService calculationService,
            AlertService alertService) {
        this.greenhouseRepository = greenhouseRepository;
        this.gravityService = gravityService;
        this.calculationService = calculationService;
        this.alertService = alertService;
    }

    @Scheduled(fixedDelayString = "${terraform.simulation.tick-ms:15000}")
    @Transactional
    public void simulateTelemetryTick() {
        greenhouseRepository.findAll().forEach(this::simulateGreenhouse);
    }

    public void simulateGreenhouse(Greenhouse greenhouse) {
        if (greenhouse.getStatus() == GreenhouseStatus.INACTIVE) {
            return;
        }

        BigDecimal gravityFactor = gravityService.calculateGravityFactor(greenhouse.getPlanet().getGravity());

        consumeResources(greenhouse.getSoil(), greenhouse.getAir(), gravityFactor);
        recalculateQualities(greenhouse);
        updatePlant(greenhouse, gravityFactor);
        updateGreenhouseStatus(greenhouse);
    }

    private void consumeResources(SoilState soil, AirState air, BigDecimal gravityFactor) {
        soil.updateHumidity(decrease(soil.getHumidity(), bd("0.75"), gravityFactor));
        soil.updateNitrogen(decrease(soil.getNitrogen(), bd("0.35"), gravityFactor));
        soil.updatePhosphorus(decrease(soil.getPhosphorus(), bd("0.20"), gravityFactor));
        soil.updatePotassium(decrease(soil.getPotassium(), bd("0.25"), gravityFactor));
        soil.updateCalcium(decrease(soil.getCalcium(), bd("0.12"), gravityFactor));
        soil.updateMagnesium(decrease(soil.getMagnesium(), bd("0.10"), gravityFactor));
        soil.updateSulfur(decrease(soil.getSulfur(), bd("0.10"), gravityFactor));

        air.updateOxygen(decrease(air.getOxygen(), bd("0.04"), gravityFactor));
        air.updateCarbonDioxide(increase(air.getCarbonDioxide(), bd("0.015"), gravityFactor, 5));
        air.updateHumidity(decrease(air.getHumidity(), bd("0.25"), gravityFactor));
    }

    private void recalculateQualities(Greenhouse greenhouse) {
        SoilState soil = greenhouse.getSoil();
        AirState air = greenhouse.getAir();
        soil.updateQuality(calculationService.calculateSoilQuality(soil));
        air.updateQuality(calculationService.calculateAirQuality(air));
    }

    private void updatePlant(Greenhouse greenhouse, BigDecimal gravityFactor) {
        PlantState plant = greenhouse.getPlant();
        if (plant.getHealthStatus() == PlantHealthStatus.DEAD || plant.getPhase() == GrowthPhase.HARVEST) {
            return;
        }

        BigDecimal minQuality = greenhouse.getSoil().getQuality().min(greenhouse.getAir().getQuality());
        if (minQuality.compareTo(bd("65")) >= 0) {
            advancePlant(plant, gravityFactor);
            updateHealth(plant, bd("0.30").multiply(gravityFactor), true);
        } else if (minQuality.compareTo(bd("35")) >= 0) {
            updateHealth(plant, bd("0.45").multiply(gravityFactor), false);
        } else {
            updateHealth(plant, bd("1.35").multiply(gravityFactor), false);
        }
    }

    private void advancePlant(PlantState plant, BigDecimal gravityFactor) {
        BigDecimal newProgress = plant.getPhaseProgress().add(bd("2.5").multiply(gravityFactor));
        if (newProgress.compareTo(bd("100")) < 0) {
            plant.updateGrowth(plant.getPhase(), clamp(newProgress, 0, 100));
            return;
        }

        int currentIndex = PHASES.indexOf(plant.getPhase());
        GrowthPhase nextPhase = currentIndex >= 0 && currentIndex + 1 < PHASES.size()
                ? PHASES.get(currentIndex + 1)
                : GrowthPhase.HARVEST;
        BigDecimal progress = nextPhase == GrowthPhase.HARVEST ? bd("100") : BigDecimal.ZERO.setScale(2);
        plant.updateGrowth(nextPhase, progress);
    }

    private void updateHealth(PlantState plant, BigDecimal delta, boolean recovery) {
        BigDecimal newHealth = recovery
                ? plant.getHealth().add(delta)
                : plant.getHealth().subtract(delta);
        newHealth = clamp(newHealth, 0, 100);

        PlantHealthStatus status;
        if (newHealth.compareTo(BigDecimal.ZERO) <= 0) {
            status = PlantHealthStatus.DEAD;
        } else if (newHealth.compareTo(bd("35")) < 0) {
            status = PlantHealthStatus.CRITICAL;
        } else if (newHealth.compareTo(bd("65")) < 0) {
            status = PlantHealthStatus.STRESSED;
        } else {
            status = PlantHealthStatus.HEALTHY;
        }

        plant.updateHealth(newHealth, status);
    }

    private void updateGreenhouseStatus(Greenhouse greenhouse) {
        OperationalStatus status = alertService.statusFromAlerts(alertService.calculateActiveAlerts(greenhouse));
        if (status == OperationalStatus.CRITICAL) {
            greenhouse.changeStatus(GreenhouseStatus.CRITICAL);
        } else if (status == OperationalStatus.ATTENTION) {
            greenhouse.changeStatus(GreenhouseStatus.ATTENTION);
        } else {
            greenhouse.changeStatus(GreenhouseStatus.OPERATIONAL);
        }
    }

    private BigDecimal decrease(BigDecimal current, BigDecimal baseRate, BigDecimal gravityFactor) {
        return clamp(current.subtract(baseRate.multiply(gravityFactor)), 0, 100);
    }

    private BigDecimal increase(BigDecimal current, BigDecimal baseRate, BigDecimal gravityFactor, double max) {
        return clamp(current.add(baseRate.multiply(gravityFactor)), 0, max);
    }

    private BigDecimal clamp(BigDecimal value, double min, double max) {
        return calculationService.clamp(value, min, max);
    }

    private BigDecimal bd(String value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
    }
}
