package br.com.fiap.terraform.dto;

import java.math.BigDecimal;

public class PlanetResponse {

    private final Long id;
    private final String code;
    private final String name;
    private final BigDecimal gravity;
    private final BigDecimal gravityFactor;
    private final String color;

    public PlanetResponse(Long id, String code, String name, BigDecimal gravity, BigDecimal gravityFactor,
            String color) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.gravity = gravity;
        this.gravityFactor = gravityFactor;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getGravity() {
        return gravity;
    }

    public BigDecimal getGravityFactor() {
        return gravityFactor;
    }

    public String getColor() {
        return color;
    }
}

