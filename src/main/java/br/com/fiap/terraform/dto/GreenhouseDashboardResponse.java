package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.GreenhouseStatus;
import br.com.fiap.terraform.enums.OperationalStatus;
import java.util.List;

public class GreenhouseDashboardResponse {

    private final Long id;
    private final String name;
    private final GreenhouseStatus status;
    private final OperationalStatus overallStatus;
    private final OperationalStatus soilStatus;
    private final OperationalStatus airStatus;
    private final PlanetResponse planet;
    private final PlantResponse plant;
    private final SoilResponse soil;
    private final AirResponse air;
    private final List<InventoryItemResponse> inventory;
    private final List<ActiveAlertResponse> activeAlerts;
    private final List<OperationLogResponse> recentLogs;

    public GreenhouseDashboardResponse(Long id, String name, GreenhouseStatus status,
            OperationalStatus overallStatus, OperationalStatus soilStatus, OperationalStatus airStatus,
            PlanetResponse planet, PlantResponse plant, SoilResponse soil, AirResponse air, List<InventoryItemResponse> inventory,
            List<ActiveAlertResponse> activeAlerts,
            List<OperationLogResponse> recentLogs) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.overallStatus = overallStatus;
        this.soilStatus = soilStatus;
        this.airStatus = airStatus;
        this.planet = planet;
        this.plant = plant;
        this.soil = soil;
        this.air = air;
        this.inventory = inventory;
        this.activeAlerts = activeAlerts;
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

    public OperationalStatus getOverallStatus() {
        return overallStatus;
    }

    public OperationalStatus getSoilStatus() {
        return soilStatus;
    }

    public OperationalStatus getAirStatus() {
        return airStatus;
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

    public List<ActiveAlertResponse> getActiveAlerts() {
        return activeAlerts;
    }

    public List<OperationLogResponse> getRecentLogs() {
        return recentLogs;
    }
}
