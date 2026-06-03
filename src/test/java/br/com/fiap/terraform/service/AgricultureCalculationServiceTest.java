package br.com.fiap.terraform.service;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.terraform.entity.AirState;
import br.com.fiap.terraform.entity.SoilState;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class AgricultureCalculationServiceTest {

    private final AgricultureCalculationService service = new AgricultureCalculationService();

    @Test
    void shouldCalculateHealthySoilQuality() {
        SoilState soil = new SoilState(
                bd("6.5"), bd("55"), bd("85"), bd("75"), bd("65"), bd("70"), bd("60"), bd("55"), bd("58")
        );

        assertThat(service.calculateSoilQuality(soil)).isEqualByComparingTo("100.00");
    }

    @Test
    void shouldCalculateHealthyAirQuality() {
        AirState air = new AirState(bd("21.0"), bd("0.04"), bd("62"), bd("90"));

        assertThat(service.calculateAirQuality(air)).isEqualByComparingTo("100.00");
    }

    private BigDecimal bd(String value) {
        return new BigDecimal(value);
    }
}

