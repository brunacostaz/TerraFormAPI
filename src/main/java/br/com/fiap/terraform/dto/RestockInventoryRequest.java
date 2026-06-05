package br.com.fiap.terraform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "Comando para registrar reposicao de estoque em uma estufa.")
public class RestockInventoryRequest {

    @NotBlank
    @Schema(description = "Codigo do recurso bruto ou composto sintetizado.", example = "H")
    private String resourceCode;

    @NotNull
    @DecimalMin("0.1")
    @Schema(description = "Quantidade percentual adicionada ao estoque. O estoque final fica limitado a 100%.", example = "15")
    private BigDecimal quantity;

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
