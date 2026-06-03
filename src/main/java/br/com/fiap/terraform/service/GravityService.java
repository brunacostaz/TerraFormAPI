package br.com.fiap.terraform.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class GravityService {

    public BigDecimal calculateGravityFactor(BigDecimal gravity) {
        return BigDecimal.valueOf(0.7)
                .add(gravity.multiply(BigDecimal.valueOf(0.3)))
                .setScale(3, RoundingMode.HALF_UP);
    }
}

