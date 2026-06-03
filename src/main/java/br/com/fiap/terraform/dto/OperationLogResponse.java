package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.AlertLevel;
import br.com.fiap.terraform.enums.LogType;
import java.time.Instant;

public class OperationLogResponse {

    private final Long id;
    private final Long greenhouseId;
    private final String greenhouseName;
    private final String planetCode;
    private final LogType type;
    private final AlertLevel alertLevel;
    private final String description;
    private final Instant createdAt;

    public OperationLogResponse(Long id, Long greenhouseId, String greenhouseName, String planetCode, LogType type,
            AlertLevel alertLevel, String description, Instant createdAt) {
        this.id = id;
        this.greenhouseId = greenhouseId;
        this.greenhouseName = greenhouseName;
        this.planetCode = planetCode;
        this.type = type;
        this.alertLevel = alertLevel;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getGreenhouseId() {
        return greenhouseId;
    }

    public String getGreenhouseName() {
        return greenhouseName;
    }

    public String getPlanetCode() {
        return planetCode;
    }

    public LogType getType() {
        return type;
    }

    public AlertLevel getAlertLevel() {
        return alertLevel;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}

