package br.com.fiap.terraform.service;

import br.com.fiap.terraform.dto.PlanetResponse;
import br.com.fiap.terraform.entity.Planet;
import br.com.fiap.terraform.exception.ResourceNotFoundException;
import br.com.fiap.terraform.repository.PlanetRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

    private final PlanetRepository planetRepository;
    private final TerraFormMapper mapper;

    public PlanetService(PlanetRepository planetRepository, TerraFormMapper mapper) {
        this.planetRepository = planetRepository;
        this.mapper = mapper;
    }

    public List<PlanetResponse> findAll() {
        return planetRepository.findAll()
                .stream()
                .map(mapper::toPlanetResponse)
                .toList();
    }

    public Planet findEntityByCode(String code) {
        return planetRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Planeta nao encontrado: " + code));
    }
}

