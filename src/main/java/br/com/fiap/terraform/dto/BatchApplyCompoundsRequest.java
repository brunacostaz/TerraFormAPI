package br.com.fiap.terraform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Schema(description = "Comando Nutrir Tudo para aplicar varios compostos em uma unica transacao.")
public class BatchApplyCompoundsRequest {

    @Valid
    @NotEmpty
    @Schema(description = "Lista de compostos selecionados para aplicacao em lote.")
    private List<BatchApplyCompoundItemRequest> applications;

    public List<BatchApplyCompoundItemRequest> getApplications() {
        return applications;
    }

    public void setApplications(List<BatchApplyCompoundItemRequest> applications) {
        this.applications = applications;
    }
}
