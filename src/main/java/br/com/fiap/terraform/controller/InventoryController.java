package br.com.fiap.terraform.controller;

import br.com.fiap.terraform.dto.InventoryItemResponse;
import br.com.fiap.terraform.dto.RestockInventoryRequest;
import br.com.fiap.terraform.service.GreenhouseService;
import br.com.fiap.terraform.service.InventoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greenhouses/{greenhouseId}/inventory")
public class InventoryController {

    private final GreenhouseService greenhouseService;
    private final InventoryService inventoryService;

    public InventoryController(GreenhouseService greenhouseService, InventoryService inventoryService) {
        this.greenhouseService = greenhouseService;
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<InventoryItemResponse> findByGreenhouseId(@PathVariable Long greenhouseId) {
        greenhouseService.findEntityById(greenhouseId);
        return inventoryService.findByGreenhouseId(greenhouseId);
    }

    @PostMapping("/restock")
    public List<InventoryItemResponse> restock(@PathVariable Long greenhouseId,
            @Valid @RequestBody RestockInventoryRequest request) {
        greenhouseService.findEntityById(greenhouseId);
        return inventoryService.restock(greenhouseId, request.getResourceCode(), request.getQuantity());
    }
}
