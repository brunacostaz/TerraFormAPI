package br.com.fiap.terraform.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ApplicationTarget {
    SOIL("Solo"),
    AIR("Ar");

    private final String label;

    ApplicationTarget(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static ApplicationTarget fromJson(String value) {
        String normalized = EnumLabel.normalize(value);
        for (ApplicationTarget target : values()) {
            if (target.name().equals(normalized) || EnumLabel.normalize(target.label).equals(normalized)) {
                return target;
            }
        }
        throw new IllegalArgumentException("Destino de aplicação inválido: " + value);
    }
}
