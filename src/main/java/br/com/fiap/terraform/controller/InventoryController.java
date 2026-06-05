package br.com.fiap.terraform.controller;

import br.com.fiap.terraform.dto.InventoryItemResponse;
import br.com.fiap.terraform.dto.RestockInventoryRequest;
import br.com.fiap.terraform.service.GreenhouseService;
import br.com.fiap.terraform.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Estoque", description = "Consulta e reposicao dos recursos independentes de cada estufa.")
@RestController
@RequestMapping("/api/greenhouses/{greenhouseId}/inventory")
public class InventoryController {

    private final GreenhouseService greenhouseService;
    private final InventoryService inventoryService;

    public InventoryController(GreenhouseService greenhouseService, InventoryService inventoryService) {
        this.greenhouseService = greenhouseService;
        this.inventoryService = inventoryService;
    }

    @Operation(
            summary = "Consulta o estoque independente de uma estufa.",
            description = "Lista elementos brutos e compostos sintetizados disponiveis na estufa informada. Cada estufa possui estoque proprio, sem compartilhar recursos com outras estufas."
    )
    @GetMapping
    public List<InventoryItemResponse> findByGreenhouseId(
            @Parameter(description = "Identificador da estufa.", example = "1")
            @PathVariable Long greenhouseId) {
        greenhouseService.findEntityById(greenhouseId);
        return inventoryService.findByGreenhouseId(greenhouseId);
    }

    @Operation(
            summary = "Repoe um recurso bruto ou composto no estoque da estufa.",
            description = "Adiciona uma quantidade percentual ao recurso informado, limita o estoque final a 100% e registra log operacional de reposicao. Use para simular recebimento de suprimentos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Recurso e quantidade percentual a repor.",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                              "resourceCode": "H",
                              "quantity": 15
                            }
                            """))
            )
    )
    @PostMapping("/restock")
    public List<InventoryItemResponse> restock(
            @Parameter(description = "Identificador da estufa.", example = "1")
            @PathVariable Long greenhouseId,
            @Valid @RequestBody RestockInventoryRequest request) {
        greenhouseService.findEntityById(greenhouseId);
        return inventoryService.restock(greenhouseId, request.getResourceCode(), request.getQuantity());
    }
}
