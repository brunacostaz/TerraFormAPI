package br.com.fiap.terraform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "planets")
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String code;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, precision = 5, scale = 3)
    private BigDecimal gravity;

    @Column(nullable = false, length = 20)
    private String color;

    public Planet() {
    }

    public Planet(String code, String name, BigDecimal gravity, String color) {
        this.code = code;
        this.name = name;
        this.gravity = gravity;
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

    public String getColor() {
        return color;
    }
}

