package br.com.fiap.terraform.controller;

import br.com.fiap.terraform.dto.ReactionResponse;
import br.com.fiap.terraform.service.ChemicalReactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Reacoes Quimicas", description = "Consulta das equacoes balanceadas usadas na sintese de compostos.")
@RestController
@RequestMapping("/api/reactions")
public class ReactionController {

    private final ChemicalReactionService chemicalReactionService;

    public ReactionController(ChemicalReactionService chemicalReactionService) {
        this.chemicalReactionService = chemicalReactionService;
    }

    @Operation(
            summary = "Lista as reacoes quimicas disponiveis para sintese.",
            description = "Retorna as reacoes balanceadas de H2O, NH3, CaCO3 e H2CO3 com equacao, finalidade, reagentes, custo por unidade e alvos em que o composto pode ser aplicado."
    )
    @GetMapping
    public List<ReactionResponse> findAll() {
        return chemicalReactionService.findAll();
    }

    @Operation(
            summary = "Consulta a reacao quimica de um composto especifico.",
            description = "Retorna a equacao balanceada, finalidade operacional, reagentes necessarios, custo por unidade e alvos permitidos para o composto informado."
    )
    @GetMapping("/{compoundCode}")
    public ReactionResponse findByCompoundCode(
            @Parameter(description = "Codigo do composto. Exemplos: H2O, NH3, CaCO3, H2CO3.", example = "NH3")
            @PathVariable String compoundCode) {
        return chemicalReactionService.findResponseByCompoundCode(compoundCode);
    }
}
