package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.GreenhouseStatus;
import java.util.List;

public class GreenhouseDashboardResponse {

    private final Long id;
    private final String name;
    private final GreenhouseStatus status;
    private final PlanetResponse planet;
    private final PlantResponse plant;
    private final SoilResponse soil;
    private final AirResponse air;
    private final List<InventoryItemResponse> inventory;
    private final List<OperationLogResponse> recentLogs;

    public GreenhouseDashboardResponse(Long id, String name, GreenhouseStatus status, PlanetResponse planet,
            PlantResponse plant, SoilResponse soil, AirResponse air, List<InventoryItemResponse> inventory,
            List<OperationLogResponse> recentLogs) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.planet = planet;
        this.plant = plant;
        this.soil = soil;
        this.air = air;
        this.inventory = inventory;
        this.recentLogs = recentLogs;
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

    public SoilResponse getSoil() {
        return soil;
    }

    public AirResponse getAir() {
        return air;
    }

    public List<InventoryItemResponse> getInventory() {
        return inventory;
    }

    public List<OperationLogResponse> getRecentLogs() {
        return recentLogs;
    }
}

