package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.GreenhouseStatus;
import br.com.fiap.terraform.enums.GrowthPhase;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "Dados para criar ou atualizar uma estufa hermetica.")
public class GreenhouseRequest {

    @NotBlank
    @Schema(description = "Nome operacional da estufa.", example = "Estufa Marte A")
    private String name;

    @NotBlank
    @Schema(description = "Codigo do planeta/lua onde a estufa esta instalada.", example = "MARS")
    private String planetCode;

    @NotNull
    @Schema(description = "Status operacional informado para a estufa.", example = "Operacional")
    private GreenhouseStatus status;

    @NotBlank
    @Schema(description = "Especie cultivada na estufa.", example = "Alface romana")
    private String plantSpecies;

    @NotNull
    @Schema(description = "Fase atual de crescimento da planta.", example = "Germinação")
    private GrowthPhase plantPhase;

    @NotNull
    @DecimalMin("0.0")
    @Schema(description = "Progresso percentual dentro da fase atual.", example = "12.5")
    private BigDecimal plantPhaseProgress;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("14.0")
    @Schema(description = "pH atual do solo hermetico.", example = "6.5")
    private BigDecimal soilPh;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Schema(description = "Umidade percentual do solo.", example = "62.0")
    private BigDecimal soilHumidity;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("30.0")
    @Schema(description = "Percentual de oxigenio no ar interno da estufa.", example = "21.0")
    private BigDecimal airOxygen;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @Schema(description = "Percentual de CO2 no ar interno da estufa.", example = "0.05")
    private BigDecimal airCarbonDioxide;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Schema(description = "Umidade percentual do ar interno.", example = "58.0")
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
