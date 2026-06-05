package br.com.fiap.terraform.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LogType {
    APPLICATION("Aplicação"),
    SYNTHESIS("Síntese"),
    REFILL("Reposição"),
    ALERT("Alerta"),
    GROWTH("Crescimento"),
    READING("Leitura");

    private final String label;

    LogType(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static LogType fromJson(String value) {
        String normalized = EnumLabel.normalize(value);
        for (LogType type : values()) {
            if (type.name().equals(normalized) || EnumLabel.normalize(type.label).equals(normalized)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de log inválido: " + value);
    }
}
