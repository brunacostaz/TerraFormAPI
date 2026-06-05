package br.com.fiap.terraform.controller;

import br.com.fiap.terraform.dto.ApplyCompoundRequest;
import br.com.fiap.terraform.dto.ApplyCompoundResponse;
import br.com.fiap.terraform.dto.SynthesisRequest;
import br.com.fiap.terraform.dto.SynthesisResponse;
import br.com.fiap.terraform.service.CompoundApplicationService;
import br.com.fiap.terraform.service.SynthesisApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Quimica Operacional", description = "Sintese quimica e aplicacao de compostos no solo/ar da estufa.")
@RestController
@RequestMapping("/api/greenhouses/{greenhouseId}")
public class ChemistryController {

    private final SynthesisApplicationService synthesisApplicationService;
    private final CompoundApplicationService compoundApplicationService;

    public ChemistryController(SynthesisApplicationService synthesisApplicationService,
            CompoundApplicationService compoundApplicationService) {
        this.synthesisApplicationService = synthesisApplicationService;
        this.compoundApplicationService = compoundApplicationService;
    }

    @Operation(
            summary = "Sintetiza um composto quimico usando integracao REST para SOAP.",
            description = "Recebe a solicitacao REST, chama o Web Service SOAP interno, valida estoque dos reagentes, debita os elementos consumidos, credita o composto produzido e registra log de sintese.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Composto e unidades de sintese solicitadas.",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                              "compoundCode": "NH3",
                              "units": 2
                            }
                            """))
            )
    )
    @PostMapping("/synthesis")
    public SynthesisResponse synthesize(
            @Parameter(description = "Identificador da estufa.", example = "1")
            @PathVariable Long greenhouseId,
            @Valid @RequestBody SynthesisRequest request) {
        return synthesisApplicationService.synthesizeThroughSoap(
                greenhouseId,
                request.getCompoundCode(),
                request.getUnits()
        );
    }

    @Operation(
            summary = "Aplica um composto sintetizado no solo ou no ar da estufa.",
            description = "Valida se o composto pode ser aplicado no alvo escolhido, verifica estoque disponivel, altera pH/umidade/nutrientes/atmosfera conforme a regra do composto, recalcula qualidade ambiental e registra log operacional.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Composto, alvo e quantidade percentual de aplicacao.",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                              "compoundCode": "H2O",
                              "target": "Solo",
                              "quantity": 10
                            }
                            """))
            )
    )
    @PostMapping("/compounds/apply")
    public ApplyCompoundResponse applyCompound(
            @Parameter(description = "Identificador da estufa.", example = "1")
            @PathVariable Long greenhouseId,
            @Valid @RequestBody ApplyCompoundRequest request) {
        return compoundApplicationService.apply(
                greenhouseId,
                request.getCompoundCode(),
                request.getTarget(),
                request.getQuantity()
        );
    }
}
