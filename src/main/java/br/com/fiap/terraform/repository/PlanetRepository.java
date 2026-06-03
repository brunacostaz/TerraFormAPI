package br.com.fiap.terraform.repository;

import br.com.fiap.terraform.entity.Planet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
    Optional<Planet> findByCode(String code);
}

