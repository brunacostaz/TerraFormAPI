package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.GrowthPhase;
import java.math.BigDecimal;

public class PlantResponse {

    private final String species;
    private final GrowthPhase phase;
    private final BigDecimal phaseProgress;

    public PlantResponse(String species, GrowthPhase phase, BigDecimal phaseProgress) {
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

