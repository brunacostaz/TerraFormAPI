package br.com.fiap.terraform.entity;

import br.com.fiap.terraform.enums.GrowthPhase;
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

    public PlantState() {
    }

    public PlantState(String species, GrowthPhase phase, BigDecimal phaseProgress) {
        this.species = species;
        this.phase = phase;
        this.phaseProgress = phaseProgress;
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
}

