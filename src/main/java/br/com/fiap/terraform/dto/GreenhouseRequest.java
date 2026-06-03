package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.GreenhouseStatus;
import br.com.fiap.terraform.enums.GrowthPhase;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class GreenhouseRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String planetCode;

    @NotNull
    private GreenhouseStatus status;

    @NotBlank
    private String plantSpecies;

    @NotNull
    private GrowthPhase plantPhase;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal plantPhaseProgress;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("14.0")
    private BigDecimal soilPh;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private BigDecimal soilHumidity;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("30.0")
    private BigDecimal airOxygen;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private BigDecimal airCarbonDioxide;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private BigDecimal airHumidity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanetCode() {
        return planetCode;
    }

    public void setPlanetCode(String planetCode) {
        this.planetCode = planetCode;
    }

    public GreenhouseStatus getStatus() {
        return status;
    }

    public void setStatus(GreenhouseStatus status) {
        this.status = status;
    }

    public String getPlantSpecies() {
        return plantSpecies;
    }

    public void setPlantSpecies(String plantSpecies) {
        this.plantSpecies = plantSpecies;
    }

    public GrowthPhase getPlantPhase() {
        return plantPhase;
    }

    public void setPlantPhase(GrowthPhase plantPhase) {
        this.plantPhase = plantPhase;
    }

    public BigDecimal getPlantPhaseProgress() {
        return plantPhaseProgress;
    }

    public void setPlantPhaseProgress(BigDecimal plantPhaseProgress) {
        this.plantPhaseProgress = plantPhaseProgress;
    }

    public BigDecimal getSoilPh() {
        return soilPh;
    }

    public void setSoilPh(BigDecimal soilPh) {
        this.soilPh = soilPh;
    }

    public BigDecimal getSoilHumidity() {
        return soilHumidity;
    }

    public void setSoilHumidity(BigDecimal soilHumidity) {
        this.soilHumidity = soilHumidity;
    }

    public BigDecimal getAirOxygen() {
        return airOxygen;
    }

    public void setAirOxygen(BigDecimal airOxygen) {
        this.airOxygen = airOxygen;
    }

    public BigDecimal getAirCarbonDioxide() {
        return airCarbonDioxide;
    }

    public void setAirCarbonDioxide(BigDecimal airCarbonDioxide) {
        this.airCarbonDioxide = airCarbonDioxide;
    }

    public BigDecimal getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(BigDecimal airHumidity) {
        this.airHumidity = airHumidity;
    }
}
