package br.com.fiap.terraform.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GrowthPhase {
    GERMINATION("Germinação"),
    SEEDLING("Muda"),
    VEGETATIVE("Vegetativo"),
    FLOWERING("Floração"),
    HARVEST("Colheita");

    private final String label;

    GrowthPhase(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static GrowthPhase fromJson(String value) {
        String normalized = EnumLabel.normalize(value);
        for (GrowthPhase phase : values()) {
            if (phase.name().equals(normalized) || EnumLabel.normalize(phase.label).equals(normalized)) {
                return phase;
            }
        }
        throw new IllegalArgumentException("Fase de crescimento inválida: " + value);
    }
}
