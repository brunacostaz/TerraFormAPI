package br.com.fiap.terraform.controller;

import br.com.fiap.terraform.dto.GreenhouseDashboardResponse;
import br.com.fiap.terraform.dto.GreenhouseRequest;
import br.com.fiap.terraform.dto.GreenhouseResponse;
import br.com.fiap.terraform.service.GreenhouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Estufas", description = "Cadastro, consulta e dashboard operacional das estufas espaciais.")
@RestController
@RequestMapping("/api/greenhouses")
public class GreenhouseController {

    private final GreenhouseService greenhouseService;

    public GreenhouseController(GreenhouseService greenhouseService) {
        this.greenhouseService = greenhouseService;
    }

    @Operation(
            summary = "Lista todas as estufas cadastradas e permite filtrar por planeta.",
            description = "Retorna as estufas com status, planeta e dados principais da planta. Use o parametro planetCode para visualizar apenas as estufas de um planeta ou lua especifica."
    )
    @GetMapping
    public List<GreenhouseResponse> findAll(
            @Parameter(description = "Filtro opcional pelo codigo do planeta. Exemplos: MOON, MARS, EUROPA, TITAN, EARTH.", example = "MARS")
            @RequestParam(required = false) String planetCode) {
        return greenhouseService.findAll(planetCode);
    }

    @Operation(
            summary = "Consulta os dados basicos de uma estufa pelo identificador.",
            description = "Retorna nome, status, planeta associado e estado atual da planta. Este endpoint e util para telas de detalhe simples, sem carregar todo o dashboard operacional."
    )
    @GetMapping("/{id}")
    public GreenhouseResponse findById(
            @Parameter(description = "Identificador da estufa.", example = "1")
            @PathVariable Long id) {
        return greenhouseService.findById(id);
    }

    @Operation(
            summary = "Consulta o dashboard completo de uma estufa.",
            description = "Retorna o painel operacional completo da estufa, incluindo status geral, status do solo, status do ar, planeta, planta, nutrientes, atmosfera, estoque, alertas ativos e logs recentes."
    )
    @GetMapping("/{id}/dashboard")
    public GreenhouseDashboardResponse getDashboard(
            @Parameter(description = "Identificador da estufa.", example = "1")
            @PathVariable Long id) {
        return greenhouseService.getDashboard(id);
    }

    @Operation(
            summary = "Cria uma nova estufa hermetica em um planeta ou lua.",
            description = "Cadastra uma estufa, associa ao planeta informado e inicializa os estados operacionais de solo, ar, planta e estoque. A criacao tambem registra um log operacional.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Dados operacionais iniciais da estufa.",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                              "name": "Estufa Marte A",
                              "planetCode": "MARS",
                              "status": "Operacional",
                              "plantSpecies": "Alface romana",
                              "plantPhase": "Germinação",
                              "plantPhaseProgress": 0,
                              "soilPh": 6.5,
                              "soilHumidity": 62,
                              "airOxygen": 21,
                              "airCarbonDioxide": 0.05,
                              "airHumidity": 58
                            }
                            """))
            )
    )
    @PostMapping
    public ResponseEntity<GreenhouseResponse> create(@Valid @RequestBody GreenhouseRequest request) {
        GreenhouseResponse response = greenhouseService.create(request);
        return ResponseEntity
                .created(URI.create("/api/greenhouses/" + response.getId()))
                .body(response);
    }

    @Operation(
            summary = "Atualiza os dados operacionais de uma estufa existente.",
            description = "Altera nome, planeta, status, planta, fase de crescimento, pH, umidade do solo, oxigenio, CO2 e umidade do ar. A atualizacao registra um log operacional.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Novo estado operacional da estufa.",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                              "name": "Estufa Marte A",
                              "planetCode": "MARS",
                              "status": "Atenção",
                              "plantSpecies": "Alface romana",
                              "plantPhase": "Muda",
                              "plantPhaseProgress": 35,
                              "soilPh": 6.2,
                              "soilHumidity": 54,
                              "airOxygen": 20.5,
                              "airCarbonDioxide": 0.12,
                              "airHumidity": 52
                            }
                            """))
            )
    )
    @PutMapping("/{id}")
    public GreenhouseResponse update(
            @Parameter(description = "Identificador da estufa.", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody GreenhouseRequest request) {
        return greenhouseService.update(id, request);
    }

    @Operation(
            summary = "Remove uma estufa e seus registros associados.",
            description = "Exclui a estufa informada pelo id e remove os logs vinculados a ela. Use com cuidado, pois a operacao altera definitivamente os dados em memoria."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identificador da estufa.", example = "1")
            @PathVariable Long id) {
        greenhouseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
