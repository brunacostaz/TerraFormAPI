package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.GreenhouseStatus;

public class GreenhouseResponse {

    private final Long id;
    private final String name;
    private final GreenhouseStatus status;
    private final PlanetResponse planet;
    private final PlantResponse plant;

    public GreenhouseResponse(Long id, String name, GreenhouseStatus status, PlanetResponse planet,
            PlantResponse plant) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.planet = planet;
        this.plant = plant;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GreenhouseStatus getStatus() {
        return status;
    }

    public PlanetResponse getPlanet() {
        return planet;
    }

    public PlantResponse getPlant() {
        return plant;
    }
}

