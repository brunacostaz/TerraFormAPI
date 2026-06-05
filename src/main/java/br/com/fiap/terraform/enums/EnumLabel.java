package br.com.fiap.terraform.enums;

import java.text.Normalizer;

final class EnumLabel {

    private EnumLabel() {
    }

    static String normalize(String value) {
        if (value == null) {
            return "";
        }
        String withoutAccents = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return withoutAccents
                .trim()
                .replace('-', '_')
                .replace(' ', '_')
                .toUpperCase();
    }
}
