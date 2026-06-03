package br.com.fiap.terraform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class SoilState {

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal ph;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal humidity;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal quality;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal nitrogen;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal phosphorus;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal potassium;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal calcium;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal magnesium;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal sulfur;

    public SoilState() {
    }

    public SoilState(BigDecimal ph, BigDecimal humidity, BigDecimal quality, BigDecimal nitrogen,
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

    public void updateQuality(BigDecimal quality) {
        this.quality = quality;
    }

    public void updateHumidity(BigDecimal humidity) {
        this.humidity = humidity;
    }

    public void updatePh(BigDecimal ph) {
        this.ph = ph;
    }

    public void updateNitrogen(BigDecimal nitrogen) {
        this.nitrogen = nitrogen;
    }

    public void updateCalcium(BigDecimal calcium) {
        this.calcium = calcium;
    }
}
