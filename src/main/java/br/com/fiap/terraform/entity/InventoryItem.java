package br.com.fiap.terraform.entity;

import br.com.fiap.terraform.enums.InventoryResourceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "greenhouse_id")
    private Greenhouse greenhouse;

    @Column(nullable = false, length = 20)
    private String resourceCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private InventoryResourceType resourceType;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal percentage;

    public InventoryItem() {
    }

    public InventoryItem(String resourceCode, InventoryResourceType resourceType, BigDecimal percentage) {
        this.resourceCode = resourceCode;
        this.resourceType = resourceType;
        this.percentage = percentage;
    }

    public Long getId() {
        return id;
    }

    public Greenhouse getGreenhouse() {
        return greenhouse;
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

    public void attachTo(Greenhouse greenhouse) {
        this.greenhouse = greenhouse;
    }

    public void changePercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }
}

