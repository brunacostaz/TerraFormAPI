package br.com.fiap.terraform.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum InventoryResourceType {
    RAW_ELEMENT("Elemento bruto"),
    SYNTHESIZED_COMPOUND("Composto sintetizado");

    private final String label;

    InventoryResourceType(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static InventoryResourceType fromJson(String value) {
        String normalized = EnumLabel.normalize(value);
        for (InventoryResourceType type : values()) {
            if (type.name().equals(normalized) || EnumLabel.normalize(type.label).equals(normalized)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de recurso inválido: " + value);
    }
}
