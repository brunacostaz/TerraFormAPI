package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.ReagentType;
import java.math.BigDecimal;

public class ReagentResponse {

    private final ReagentType type;
    private final String code;
    private final BigDecimal costPerUnit;

    public ReagentResponse(ReagentType type, String code, BigDecimal costPerUnit) {
        this.type = type;
        this.code = code;
        this.costPerUnit = costPerUnit;
    }

    public ReagentType getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getCostPerUnit() {
        return costPerUnit;
    }
}

