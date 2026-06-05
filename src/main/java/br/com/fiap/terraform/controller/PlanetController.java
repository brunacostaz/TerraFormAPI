package br.com.fiap.terraform.controller;

import br.com.fiap.terraform.dto.PlanetResponse;
import br.com.fiap.terraform.service.PlanetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Planetas", description = "Consulta dos planetas e luas disponiveis para instalacao das estufas.")
@RestController
@RequestMapping("/api/planets")
public class PlanetController {

    private final PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @Operation(
            summary = "Lista os planetas e luas disponiveis para as estufas.",
            description = "Retorna Lua, Marte, Europa, Tita e Terra com gravidade, cor de identificacao e fator de consumo calculado pela formula gravityFactor = 0.7 + gravidade * 0.3."
    )
    @GetMapping
    public List<PlanetResponse> findAll() {
        return planetService.findAll();
    }
}
