package br.com.fiap.terraform.service;

import br.com.fiap.terraform.entity.AirState;
import br.com.fiap.terraform.entity.SoilState;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AgricultureCalculationService {

    public BigDecimal calculateSoilQuality(SoilState soil) {
        List<Double> scores = List.of(
                nutrientScore(soil.getNitrogen()),
                nutrientScore(soil.getPhosphorus()),
                nutrientScore(soil.getPotassium()),
                nutrientScore(soil.getCalcium()),
                nutrientScore(soil.getMagnesium()),
                nutrientScore(soil.getSulfur())
        );

        double nutrientMin = scores.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        double nutrientAvg = scores.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double phScore = phScore(soil.getPh());
        double humidityScore = humidityScore(soil.getHumidity());

        double avg = nutrientAvg * 0.5 + phScore * 0.2 + humidityScore * 0.3;
        double worst = Math.min(nutrientMin, Math.min(phScore, humidityScore));
        return roundedPercent((avg * 0.45 + worst * 0.55) * 100);
    }

    public BigDecimal calculateAirQuality(AirState air) {
        double oxygenScore = oxygenScore(air.getOxygen());
        double carbonDioxideScore = carbonDioxideScore(air.getCarbonDioxide());
        double humidityScore = humidityScore(air.getHumidity());

        double avg = oxygenScore * 0.45 + carbonDioxideScore * 0.35 + humidityScore * 0.2;
        double worst = Math.min(oxygenScore, Math.min(carbonDioxideScore, humidityScore));
        return roundedPercent((avg * 0.45 + worst * 0.55) * 100);
    }

    public BigDecimal clamp(BigDecimal value, double min, double max) {
        double clamped = Math.max(min, Math.min(max, value.doubleValue()));
        return BigDecimal.valueOf(clamped).setScale(2, RoundingMode.HALF_UP);
    }

    private double nutrientScore(BigDecimal value) {
        double v = value.doubleValue();
        if (v >= 40 && v <= 80) {
            return 1;
        }
        if (v <= 15) {
            return 0;
        }
        if (v < 25) {
            return ramp(v, 15, 25) * 0.25;
        }
        if (v < 40) {
            return 0.25 + ramp(v, 25, 40) * 0.75;
        }
        return 1 - ramp(v, 80, 100) * 0.15;
    }

    private double phScore(BigDecimal value) {
        double ph = value.doubleValue();
        if (ph >= 6.0 && ph <= 7.0) {
            return 1;
        }
        if (ph <= 5.0 || ph >= 8.0) {
            return 0;
        }
        if (ph < 5.5) {
            return ramp(ph, 5.0, 5.5) * 0.3;
        }
        if (ph < 6.0) {
            return 0.3 + ramp(ph, 5.5, 6.0) * 0.7;
        }
        if (ph <= 7.5) {
            return 1 - ramp(ph, 7.0, 7.5) * 0.7;
        }
        return 0.3 - ramp(ph, 7.5, 8.0) * 0.3;
    }

    private double humidityScore(BigDecimal value) {
        double humidity = value.doubleValue();
        if (humidity >= 40 && humidity <= 85) {
            return 1;
        }
        if (humidity <= 15) {
            return 0;
        }
        if (humidity < 25) {
            return ramp(humidity, 15, 25) * 0.25;
        }
        if (humidity < 40) {
            return 0.25 + ramp(humidity, 25, 40) * 0.75;
        }
        return 1 - ramp(humidity, 85, 100) * 0.5;
    }

    private double oxygenScore(BigDecimal value) {
        double oxygen = value.doubleValue();
        if (oxygen >= 19 && oxygen <= 22) {
            return 1;
        }
        if (oxygen <= 16) {
            return 0;
        }
        if (oxygen < 18) {
            return ramp(oxygen, 16, 18) * 0.25;
        }
        if (oxygen < 19) {
            return 0.25 + ramp(oxygen, 18, 19) * 0.75;
        }
        if (oxygen <= 26) {
            return 1 - ramp(oxygen, 22, 26) * 0.7;
        }
        return Math.max(0, Math.min(1, 0.3 - ramp(oxygen, 26, 30) * 0.3));
    }

    private double carbonDioxideScore(BigDecimal value) {
        double carbonDioxide = value.doubleValue();
        if (carbonDioxide <= 0.08) {
            return 1;
        }
        if (carbonDioxide >= 1.5) {
            return 0;
        }
        if (carbonDioxide <= 0.5) {
            return 1 - ramp(carbonDioxide, 0.08, 0.5) * 0.5;
        }
        return 0.5 - ramp(carbonDioxide, 0.5, 1.5) * 0.5;
    }

    private double ramp(double value, double low, double high) {
        if (value <= low) {
            return 0;
        }
        if (value >= high) {
            return 1;
        }
        return (value - low) / (high - low);
    }

    private BigDecimal roundedPercent(double value) {
        long rounded = Math.round(Math.max(0, Math.min(100, value)));
        return BigDecimal.valueOf(rounded).setScale(2, RoundingMode.HALF_UP);
    }
}

