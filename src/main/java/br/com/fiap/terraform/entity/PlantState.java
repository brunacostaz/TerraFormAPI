package br.com.fiap.terraform.entity;

import br.com.fiap.terraform.enums.GrowthPhase;
import br.com.fiap.terraform.enums.PlantHealthStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;

@Embeddable
public class PlantState {

    @Column(nullable = false, length = 60)
    private String species;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private GrowthPhase phase;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal phaseProgress;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal health;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PlantHealthStatus healthStatus;

    public PlantState() {
    }

    public PlantState(String species, GrowthPhase phase, BigDecimal phaseProgress) {
        this(species, phase, phaseProgress, BigDecimal.valueOf(100), PlantHealthStatus.HEALTHY);
    }

    public PlantState(String species, GrowthPhase phase, BigDecimal phaseProgress, BigDecimal health,
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

    public void updateGrowth(GrowthPhase phase, BigDecimal phaseProgress) {
        this.phase = phase;
        this.phaseProgress = phaseProgress;
    }

    public void updateHealth(BigDecimal health, PlantHealthStatus healthStatus) {
        this.health = health;
        this.healthStatus = healthStatus;
    }
}
