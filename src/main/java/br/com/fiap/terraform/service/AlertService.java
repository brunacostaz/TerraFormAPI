package br.com.fiap.terraform.service;

import br.com.fiap.terraform.dto.ActiveAlertResponse;
import br.com.fiap.terraform.entity.AirState;
import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.entity.InventoryItem;
import br.com.fiap.terraform.entity.PlantState;
import br.com.fiap.terraform.entity.SoilState;
import br.com.fiap.terraform.enums.AlertLevel;
import br.com.fiap.terraform.enums.OperationalStatus;
import br.com.fiap.terraform.enums.PlantHealthStatus;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    public List<ActiveAlertResponse> calculateActiveAlerts(Greenhouse greenhouse) {
        List<ActiveAlertResponse> alerts = new ArrayList<>();
        SoilState soil = greenhouse.getSoil();
        AirState air = greenhouse.getAir();
        PlantState plant = greenhouse.getPlant();

        addQualityAlert(alerts, "Solo", "Qualidade do solo", soil.getQuality());
        addQualityAlert(alerts, "Ar", "Qualidade do ar", air.getQuality());

        addRangeAlert(alerts, "pH do solo", "pH do solo", soil.getPh(), bd("5.0"), bd("8.0"), bd("6.0"), bd("7.0"));
        addRangeAlert(alerts, "Umidade do solo", "Umidade do solo", soil.getHumidity(), bd("15"), bd("100"),
                bd("40"), bd("85"));
        addRangeAlert(alerts, "Oxigênio atmosférico", "Oxigênio atmosférico", air.getOxygen(), bd("16"), bd("30"),
                bd("19"), bd("22"));
        addCarbonDioxideAlert(alerts, air.getCarbonDioxide());

        addNutrientAlert(alerts, "N", soil.getNitrogen());
        addNutrientAlert(alerts, "P", soil.getPhosphorus());
        addNutrientAlert(alerts, "K", soil.getPotassium());
        addNutrientAlert(alerts, "Ca", soil.getCalcium());
        addNutrientAlert(alerts, "Mg", soil.getMagnesium());
        addNutrientAlert(alerts, "S", soil.getSulfur());

        if (plant.getHealthStatus() == PlantHealthStatus.DEAD) {
            alerts.add(new ActiveAlertResponse(AlertLevel.CRITICAL, "Planta", "Planta sem viabilidade biológica."));
        } else if (plant.getHealth().compareTo(bd("35")) < 0) {
            alerts.add(new ActiveAlertResponse(AlertLevel.CRITICAL, "Planta", "Saúde da planta em estado crítico."));
        } else if (plant.getHealth().compareTo(bd("65")) < 0) {
            alerts.add(new ActiveAlertResponse(AlertLevel.ATTENTION, "Planta", "Planta sob estresse fisiológico."));
        }

        greenhouse.getInventory().forEach(item -> addInventoryAlert(alerts, item));

        return alerts.stream()
                .sorted(Comparator.comparing(alert -> alert.getLevel() == AlertLevel.CRITICAL ? 0 : 1))
                .toList();
    }

    public OperationalStatus statusFromQuality(BigDecimal quality) {
        if (quality.compareTo(bd("35")) < 0) {
            return OperationalStatus.CRITICAL;
        }
        if (quality.compareTo(bd("65")) < 0) {
            return OperationalStatus.ATTENTION;
        }
        return OperationalStatus.OPTIMAL;
    }

    public OperationalStatus statusFromAlerts(List<ActiveAlertResponse> alerts) {
        boolean critical = alerts.stream().anyMatch(alert -> alert.getLevel() == AlertLevel.CRITICAL);
        if (critical) {
            return OperationalStatus.CRITICAL;
        }
        boolean attention = alerts.stream().anyMatch(alert -> alert.getLevel() == AlertLevel.ATTENTION);
        return attention ? OperationalStatus.ATTENTION : OperationalStatus.OPTIMAL;
    }

    private void addQualityAlert(List<ActiveAlertResponse> alerts, String source, String label, BigDecimal quality) {
        OperationalStatus status = statusFromQuality(quality);
        if (status == OperationalStatus.CRITICAL) {
            alerts.add(new ActiveAlertResponse(AlertLevel.CRITICAL, source, label + " crítica: " + quality + "%."));
        } else if (status == OperationalStatus.ATTENTION) {
            alerts.add(new ActiveAlertResponse(AlertLevel.ATTENTION, source, label + " em atenção: " + quality + "%."));
        }
    }

    private void addRangeAlert(List<ActiveAlertResponse> alerts, String source, String label, BigDecimal value,
            BigDecimal criticalLow, BigDecimal criticalHigh, BigDecimal idealLow, BigDecimal idealHigh) {
        if (value.compareTo(criticalLow) <= 0 || value.compareTo(criticalHigh) >= 0) {
            alerts.add(new ActiveAlertResponse(AlertLevel.CRITICAL, source, label + " fora da faixa segura: " + value + "."));
        } else if (value.compareTo(idealLow) < 0 || value.compareTo(idealHigh) > 0) {
            alerts.add(new ActiveAlertResponse(AlertLevel.ATTENTION, source, label + " fora da faixa ideal: " + value + "."));
        }
    }

    private void addCarbonDioxideAlert(List<ActiveAlertResponse> alerts, BigDecimal carbonDioxide) {
        if (carbonDioxide.compareTo(bd("1.5")) >= 0) {
            alerts.add(new ActiveAlertResponse(AlertLevel.CRITICAL, "CO2 atmosférico", "CO2 atmosférico crítico: "
                    + carbonDioxide + "%."));
        } else if (carbonDioxide.compareTo(bd("0.5")) > 0) {
            alerts.add(new ActiveAlertResponse(AlertLevel.ATTENTION, "CO2 atmosférico", "CO2 atmosférico elevado: "
                    + carbonDioxide + "%."));
        }
    }

    private void addNutrientAlert(List<ActiveAlertResponse> alerts, String nutrient, BigDecimal value) {
        if (value.compareTo(bd("15")) <= 0) {
            alerts.add(new ActiveAlertResponse(AlertLevel.CRITICAL, "Nutriente " + nutrient,
                    nutrient + " crítico no solo: " + value + "%."));
        } else if (value.compareTo(bd("25")) < 0) {
            alerts.add(new ActiveAlertResponse(AlertLevel.ATTENTION, "Nutriente " + nutrient,
                    nutrient + " baixo no solo: " + value + "%."));
        }
    }

    private void addInventoryAlert(List<ActiveAlertResponse> alerts, InventoryItem item) {
        if (item.getPercentage().compareTo(bd("10")) <= 0) {
            alerts.add(new ActiveAlertResponse(AlertLevel.CRITICAL, "Estoque " + item.getResourceCode(),
                    "Estoque crítico de " + item.getResourceCode() + ": " + item.getPercentage() + "%."));
        } else if (item.getPercentage().compareTo(bd("25")) < 0) {
            alerts.add(new ActiveAlertResponse(AlertLevel.ATTENTION, "Estoque " + item.getResourceCode(),
                    "Estoque baixo de " + item.getResourceCode() + ": " + item.getPercentage() + "%."));
        }
    }

    private BigDecimal bd(String value) {
        return new BigDecimal(value);
    }
}
