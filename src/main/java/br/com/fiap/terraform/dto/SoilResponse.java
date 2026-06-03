package br.com.fiap.terraform.dto;

import java.math.BigDecimal;

public class SoilResponse {

    private final BigDecimal ph;
    private final BigDecimal humidity;
    private final BigDecimal quality;
    private final BigDecimal nitrogen;
    private final BigDecimal phosphorus;
    private final BigDecimal potassium;
    private final BigDecimal calcium;
    private final BigDecimal magnesium;
    private final BigDecimal sulfur;

    public SoilResponse(BigDecimal ph, BigDecimal humidity, BigDecimal quality, BigDecimal nitrogen,
            BigDecimal phosphorus, BigDecimal potassium, BigDecimal calcium, BigDecimal magnesium,
            BigDecimal sulfur) {
        this.ph = ph;
        this.humidity = humidity;
        this.quality = quality;
        this.nitrogen = nitrogen;
        this.phosphorus = phosphorus;
        this.potassium = potassium;
        this.calcium = calcium;
        this.magnesium = magnesium;
        this.sulfur = sulfur;
    }

    public BigDecimal getPh() {
        return ph;
    }

    public BigDecimal getHumidity() {
        return humidity;
    }

    public BigDecimal getQuality() {
        return quality;
    }

    public BigDecimal getNitrogen() {
        return nitrogen;
    }

    public BigDecimal getPhosphorus() {
        return phosphorus;
    }

    public BigDecimal getPotassium() {
        return potassium;
    }

    public BigDecimal getCalcium() {
        return calcium;
    }

    public BigDecimal getMagnesium() {
        return magnesium;
    }

    public BigDecimal getSulfur() {
        return sulfur;
    }
}

