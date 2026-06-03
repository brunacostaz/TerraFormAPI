package br.com.fiap.terraform.controller;

import br.com.fiap.terraform.dto.PlanetResponse;
import br.com.fiap.terraform.service.PlanetService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/planets")
public class PlanetController {

    private final PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @GetMapping
    public List<PlanetResponse> findAll() {
        return planetService.findAll();
    }
}

