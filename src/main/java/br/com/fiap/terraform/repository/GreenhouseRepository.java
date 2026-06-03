package br.com.fiap.terraform.repository;

import br.com.fiap.terraform.entity.Greenhouse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreenhouseRepository extends JpaRepository<Greenhouse, Long> {
    List<Greenhouse> findByPlanetCode(String planetCode);
}

