package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.GrowthPhase;
import br.com.fiap.terraform.enums.PlantHealthStatus;
import java.math.BigDecimal;

public class PlantResponse {

    private final String species;
    private final GrowthPhase phase;
    private final BigDecimal phaseProgress;
    private final BigDecimal health;
    private final PlantHealthStatus healthStatus;

    public PlantResponse(String species, GrowthPhase phase, BigDecimal phaseProgress, BigDecimal health,
            PlantHealthStatus healthStatus) {
        this.species = species;
        this.phase = phase;
        this.phaseProgress = phaseProgress;
        this.health = health;
        this.healthStatus = healthStatus;
    }

    public String getSpecies() {
        return species;
    }

    public GrowthPhase getPhase() {
        return phase;
    }

    public BigDecimal getPhaseProgress() {
        return phaseProgress;
    }

    public BigDecimal getHealth() {
        return health;
    }

    public PlantHealthStatus getHealthStatus() {
        return healthStatus;
    }
}
