package br.com.fiap.terraform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class AirState {

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal oxygen;

    @Column(nullable = false, precision = 6, scale = 3)
    private BigDecimal carbonDioxide;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal humidity;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal quality;

    public AirState() {
    }

    public AirState(BigDecimal oxygen, BigDecimal carbonDioxide, BigDecimal humidity, BigDecimal quality) {
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

    public void updateOxygen(BigDecimal oxygen) {
        this.oxygen = oxygen;
    }

    public void updateCarbonDioxide(BigDecimal carbonDioxide) {
        this.carbonDioxide = carbonDioxide;
    }

    public void updateHumidity(BigDecimal humidity) {
        this.humidity = humidity;
    }

    public void updateQuality(BigDecimal quality) {
        this.quality = quality;
    }
}
