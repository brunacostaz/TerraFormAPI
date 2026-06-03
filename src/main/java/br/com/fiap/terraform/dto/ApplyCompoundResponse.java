package br.com.fiap.terraform.dto;

public class ApplyCompoundResponse {

    private final String message;
    private final GreenhouseDashboardResponse dashboard;

    public ApplyCompoundResponse(String message, GreenhouseDashboardResponse dashboard) {
        this.message = message;
        this.dashboard = dashboard;
    }

    public String getMessage() {
        return message;
    }

    public GreenhouseDashboardResponse getDashboard() {
        return dashboard;
    }
}

