package br.com.fiap.terraform.controller;

import br.com.fiap.terraform.dto.OperationLogResponse;
import br.com.fiap.terraform.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Logs", description = "Consulta do historico operacional das estufas.")
@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @Operation(
            summary = "Consulta o historico de acoes operacionais das estufas.",
            description = "Lista logs globais ou filtrados por estufa/planeta. Os logs representam acoes explicitas, como criacao, atualizacao, reposicao, sintese e aplicacao. A simulacao automatica de telemetria nao gera logs."
    )
    @GetMapping
    public List<OperationLogResponse> findLogs(
            @Parameter(description = "Filtro opcional pelo id da estufa.", example = "1")
            @RequestParam(required = false) Long greenhouseId,
            @Parameter(description = "Filtro opcional pelo codigo do planeta.", example = "MARS")
            @RequestParam(required = false) String planetCode) {
        return logService.findLogs(greenhouseId, planetCode);
    }
}
