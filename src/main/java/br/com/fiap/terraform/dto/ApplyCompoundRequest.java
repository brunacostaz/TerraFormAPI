package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.ApplicationTarget;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ApplyCompoundRequest {

    @NotBlank
    private String compoundCode;

    @NotNull
    private ApplicationTarget target;

    @NotNull
    @DecimalMin("0.1")
    private BigDecimal quantity;

    public String getCompoundCode() {
        return compoundCode;
    }

    public void setCompoundCode(String compoundCode) {
        this.compoundCode = compoundCode;
    }

    public ApplicationTarget getTarget() {
        return target;
    }

    public void setTarget(ApplicationTarget target) {
        this.target = target;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}

