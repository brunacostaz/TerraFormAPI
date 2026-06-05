package br.com.fiap.terraform.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import br.com.fiap.terraform.entity.AirState;
import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.entity.Planet;
import br.com.fiap.terraform.entity.PlantState;
import br.com.fiap.terraform.entity.SoilState;
import br.com.fiap.terraform.enums.GreenhouseStatus;
import br.com.fiap.terraform.enums.GrowthPhase;
import br.com.fiap.terraform.repository.GreenhouseRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class SimulationServiceTest {

    @Test
    void shouldConsumeMoreSoilHumidityOnEarthThanOnMoonBecauseOfGravity() {
        SimulationService simulationService = new SimulationService(
                mock(GreenhouseRepository.class),
                new GravityService(),
                new AgricultureCalculationService(),
                new AlertService()
        );
        Greenhouse moonGreenhouse = greenhouse(new Planet("lua", "Lua", bd("0.17"), "#A0A0A8"));
        Greenhouse earthGreenhouse = greenhouse(new Planet("terra", "Terra", bd("1.00"), "#2E8B57"));

        simulationService.simulateGreenhouse(moonGreenhouse);
        simulationService.simulateGreenhouse(earthGreenhouse);

        assertThat(earthGreenhouse.getSoil().getHumidity())
                .isLessThan(moonGreenhouse.getSoil().getHumidity());
    }

    private Greenhouse greenhouse(Planet planet) {
        return new Greenhouse(
                "Estufa",
                GreenhouseStatus.OPERATIONAL,
                planet,
                new SoilState(bd("6.5"), bd("55"), bd("85"), bd("75"), bd("65"), bd("70"), bd("60"), bd("55"), bd("58")),
                new AirState(bd("21"), bd("0.04"), bd("62"), bd("90")),
                new PlantState("alface", GrowthPhase.VEGETATIVE, bd("42"))
        );
    }

    private BigDecimal bd(String value) {
        return new BigDecimal(value);
    }
}
