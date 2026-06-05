package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.AlertLevel;

public class ActiveAlertResponse {

    private final AlertLevel level;
    private final String source;
    private final String message;

    public ActiveAlertResponse(AlertLevel level, String source, String message) {
        this.level = level;
        this.source = source;
        this.message = message;
    }

    public AlertLevel getLevel() {
        return level;
    }

    public String getSource() {
        return source;
    }

    public String getMessage() {
        return message;
    }
}
