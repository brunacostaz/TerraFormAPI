package br.com.fiap.terraform.repository;

import br.com.fiap.terraform.entity.InventoryItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    List<InventoryItem> findByGreenhouseIdOrderByResourceTypeAscResourceCodeAsc(Long greenhouseId);
    Optional<InventoryItem> findByGreenhouseIdAndResourceCode(Long greenhouseId, String resourceCode);
}

