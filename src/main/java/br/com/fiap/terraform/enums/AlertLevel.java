package br.com.fiap.terraform.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AlertLevel {
    ATTENTION("Atenção"),
    CRITICAL("Crítico");

    private final String label;

    AlertLevel(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static AlertLevel fromJson(String value) {
        String normalized = EnumLabel.normalize(value);
        for (AlertLevel level : values()) {
            if (level.name().equals(normalized) || EnumLabel.normalize(level.label).equals(normalized)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Nível de alerta inválido: " + value);
    }
}
