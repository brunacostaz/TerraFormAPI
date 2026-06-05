package br.com.fiap.terraform.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PlantHealthStatus {
    HEALTHY("Saudável"),
    STRESSED("Estressada"),
    CRITICAL("Crítica"),
    DEAD("Morta");

    private final String label;

    PlantHealthStatus(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static PlantHealthStatus fromJson(String value) {
        String normalized = EnumLabel.normalize(value);
        for (PlantHealthStatus status : values()) {
            if (status.name().equals(normalized) || EnumLabel.normalize(status.label).equals(normalized)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de saúde da planta inválido: " + value);
    }
}
