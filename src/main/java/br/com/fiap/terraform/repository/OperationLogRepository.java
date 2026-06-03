package br.com.fiap.terraform.repository;

import br.com.fiap.terraform.entity.OperationLog;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationLogRepository extends JpaRepository<OperationLog, Long> {
    List<OperationLog> findTop100ByOrderByCreatedAtDesc();
    List<OperationLog> findTop100ByGreenhouseIdOrderByCreatedAtDesc(Long greenhouseId);
    List<OperationLog> findTop100ByGreenhousePlanetCodeOrderByCreatedAtDesc(String planetCode);

    @Modifying
    void deleteByGreenhouseId(Long greenhouseId);
}
