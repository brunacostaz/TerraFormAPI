package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.ApplicationTarget;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "Item de aplicacao de composto usado no fluxo Nutrir Tudo.")
public class BatchApplyCompoundItemRequest {

    @NotBlank
    @Schema(description = "Codigo do composto sintetizado.", example = "NH3")
    private String compoundCode;

    @NotNull
    @Schema(description = "Alvo da aplicacao.", example = "Solo")
    private ApplicationTarget target;

    @NotNull
    @DecimalMin("0.1")
    @Schema(description = "Quantidade percentual solicitada.", example = "10")
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
