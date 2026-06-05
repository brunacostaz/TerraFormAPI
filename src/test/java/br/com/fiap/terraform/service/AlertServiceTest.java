package br.com.fiap.terraform.service;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.terraform.dto.ActiveAlertResponse;
import br.com.fiap.terraform.entity.AirState;
import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.entity.Planet;
import br.com.fiap.terraform.entity.PlantState;
import br.com.fiap.terraform.entity.SoilState;
import br.com.fiap.terraform.enums.AlertLevel;
import br.com.fiap.terraform.enums.GreenhouseStatus;
import br.com.fiap.terraform.enums.GrowthPhase;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class AlertServiceTest {

    private final AlertService alertService = new AlertService();

    @Test
    void shouldGenerateCriticalAlertWhenSoilQualityIsCritical() {
        Greenhouse greenhouse = new Greenhouse(
                "Estufa Critica",
                GreenhouseStatus.OPERATIONAL,
                new Planet("marte", "Marte", bd("0.38"), "#C1440E"),
                new SoilState(bd("4.8"), bd("10"), bd("20"), bd("12"), bd("40"), bd("40"), bd("40"), bd("40"), bd("40")),
                new AirState(bd("21"), bd("0.04"), bd("60"), bd("90")),
                new PlantState("alface", GrowthPhase.VEGETATIVE, bd("40"))
        );

        List<ActiveAlertResponse> alerts = alertService.calculateActiveAlerts(greenhouse);

        assertThat(alerts)
                .anyMatch(alert -> alert.getLevel() == AlertLevel.CRITICAL && alert.getSource().equals("Solo"));
    }

    private BigDecimal bd(String value) {
        return new BigDecimal(value);
    }
}
