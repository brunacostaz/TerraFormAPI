package br.com.fiap.terraform.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GreenhouseStatus {
    OPERATIONAL("Operacional"),
    ATTENTION("Atenção"),
    CRITICAL("Crítico"),
    INACTIVE("Inativa");

    private final String label;

    GreenhouseStatus(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static GreenhouseStatus fromJson(String value) {
        String normalized = EnumLabel.normalize(value);
        for (GreenhouseStatus status : values()) {
            if (status.name().equals(normalized) || EnumLabel.normalize(status.label).equals(normalized)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de estufa inválido: " + value);
    }
}
