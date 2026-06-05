package br.com.fiap.terraform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Comando para sintetizar um composto quimico no estoque da estufa.")
public class SynthesisRequest {

    @NotBlank
    @Schema(description = "Codigo do composto a ser sintetizado.", example = "NH3", allowableValues = {
            "H2O", "NH3", "CaCO3", "H2CO3"
    })
    private String compoundCode;

    @Min(1)
    @Schema(description = "Quantidade de unidades de sintese. Cada unidade consome reagentes e gera percentual do composto.", example = "2")
    private int units;

    public String getCompoundCode() {
        return compoundCode;
    }

    public void setCompoundCode(String compoundCode) {
        this.compoundCode = compoundCode;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
