package br.com.fiap.terraform.dto;

import java.math.BigDecimal;
import java.util.List;

public class SynthesisResponse {

    private final Long greenhouseId;
    private final String compoundCode;
    private final int units;
    private final BigDecimal producedPercentage;
    private final String message;
    private final List<InventoryItemResponse> inventory;

    public SynthesisResponse(Long greenhouseId, String compoundCode, int units, BigDecimal producedPercentage,
            String message, List<InventoryItemResponse> inventory) {
        this.greenhouseId = greenhouseId;
        this.compoundCode = compoundCode;
        this.units = units;
        this.producedPercentage = producedPercentage;
        this.message = message;
        this.inventory = inventory;
    }

    public Long getGreenhouseId() {
        return greenhouseId;
    }

    public String getCompoundCode() {
        return compoundCode;
    }

    public int getUnits() {
        return units;
    }

    public BigDecimal getProducedPercentage() {
        return producedPercentage;
    }

    public String getMessage() {
        return message;
    }

    public List<InventoryItemResponse> getInventory() {
        return inventory;
    }
}

