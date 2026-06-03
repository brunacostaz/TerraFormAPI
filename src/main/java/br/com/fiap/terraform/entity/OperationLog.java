package br.com.fiap.terraform.entity;

import br.com.fiap.terraform.enums.AlertLevel;
import br.com.fiap.terraform.enums.LogType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "operation_logs")
public class OperationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "greenhouse_id")
    private Greenhouse greenhouse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private LogType type;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private AlertLevel alertLevel;

    @Column(nullable = false, length = 300)
    private String description;

    @Column(nullable = false)
    private Instant createdAt;

    public OperationLog() {
    }

    public OperationLog(Greenhouse greenhouse, LogType type, AlertLevel alertLevel, String description) {
        this.greenhouse = greenhouse;
        this.type = type;
        this.alertLevel = alertLevel;
        this.description = description;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Greenhouse getGreenhouse() {
        return greenhouse;
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

