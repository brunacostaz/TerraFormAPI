package br.com.fiap.terraform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Instant;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health", description = "Verificacao de disponibilidade da API.")
@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Operation(
            summary = "Verifica se a TerraForm API esta ativa e pronta para receber requisicoes.",
            description = "Use este endpoint como health check da aplicacao. Ele retorna o status do servico, o nome da API e a data/hora atual do backend."
    )
    @GetMapping
    public Map<String, Object> health() {
        return Map.of(
                "status", "Ativo",
                "servico", "TerraForm API",
                "dataHora", Instant.now()
        );
    }
}
