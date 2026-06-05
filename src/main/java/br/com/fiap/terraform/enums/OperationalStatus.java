package br.com.fiap.terraform.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OperationalStatus {
    OPTIMAL("Ótimo"),
    ATTENTION("Atenção"),
    CRITICAL("Crítico");

    private final String label;

    OperationalStatus(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static OperationalStatus fromJson(String value) {
        String normalized = EnumLabel.normalize(value);
        for (OperationalStatus status : values()) {
            if (status.name().equals(normalized) || EnumLabel.normalize(status.label).equals(normalized)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status operacional inválido: " + value);
    }
}
