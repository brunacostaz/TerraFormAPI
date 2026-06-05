package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.ApplicationTarget;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "Comando para aplicar um composto sintetizado no solo ou no ar da estufa.")
public class ApplyCompoundRequest {

    @NotBlank
    @Schema(description = "Codigo do composto sintetizado.", example = "H2O", allowableValues = {
            "H2O", "NH3", "CaCO3", "H2CO3"
    })
    private String compoundCode;

    @NotNull
    @Schema(description = "Alvo da aplicacao. Aceita valores em portugues ou os codigos tecnicos SOIL/AIR.", example = "Solo")
    private ApplicationTarget target;

    @NotNull
    @DecimalMin("0.1")
    @Schema(description = "Quantidade percentual solicitada para aplicacao.", example = "10")
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
