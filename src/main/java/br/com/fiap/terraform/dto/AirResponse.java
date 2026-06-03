package br.com.fiap.terraform.dto;

import java.math.BigDecimal;

public class AirResponse {

    private final BigDecimal oxygen;
    private final BigDecimal carbonDioxide;
    private final BigDecimal humidity;
    private final BigDecimal quality;

    public AirResponse(BigDecimal oxygen, BigDecimal carbonDioxide, BigDecimal humidity, BigDecimal quality) {
        this.oxygen = oxygen;
        this.carbonDioxide = carbonDioxide;
        this.humidity = humidity;
        this.quality = quality;
    }

    public BigDecimal getOxygen() {
        return oxygen;
    }

    public BigDecimal getCarbonDioxide() {
        return carbonDioxide;
    }

    public BigDecimal getHumidity() {
        return humidity;
    }

    public BigDecimal getQuality() {
        return quality;
    }
}

