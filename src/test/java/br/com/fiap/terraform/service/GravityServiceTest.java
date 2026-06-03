package br.com.fiap.terraform.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class GravityServiceTest {

    private final GravityService gravityService = new GravityService();

    @Test
    void shouldCalculateMarsGravityFactor() {
        BigDecimal result = gravityService.calculateGravityFactor(new BigDecimal("0.38"));

        assertThat(result).isEqualByComparingTo("0.814");
    }

    @Test
    void shouldCalculateEarthGravityFactor() {
        BigDecimal result = gravityService.calculateGravityFactor(new BigDecimal("1.00"));

        assertThat(result).isEqualByComparingTo("1.000");
    }
}

