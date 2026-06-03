package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.InventoryResourceType;
import java.math.BigDecimal;

public class InventoryItemResponse {

    private final String resourceCode;
    private final InventoryResourceType resourceType;
    private final BigDecimal percentage;

    public InventoryItemResponse(String resourceCode, InventoryResourceType resourceType, BigDecimal percentage) {
        this.resourceCode = resourceCode;
        this.resourceType = resourceType;
        this.percentage = percentage;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public InventoryResourceType getResourceType() {
        return resourceType;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }
}

