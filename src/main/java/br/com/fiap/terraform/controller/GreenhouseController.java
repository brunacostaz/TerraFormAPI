package br.com.fiap.terraform.controller;

import br.com.fiap.terraform.dto.GreenhouseDashboardResponse;
import br.com.fiap.terraform.dto.GreenhouseRequest;
import br.com.fiap.terraform.dto.GreenhouseResponse;
import br.com.fiap.terraform.service.GreenhouseService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greenhouses")
public class GreenhouseController {

    private final GreenhouseService greenhouseService;

    public GreenhouseController(GreenhouseService greenhouseService) {
        this.greenhouseService = greenhouseService;
    }

    @GetMapping
    public List<GreenhouseResponse> findAll(@RequestParam(required = false) String planetCode) {
        return greenhouseService.findAll(planetCode);
    }

    @GetMapping("/{id}")
    public GreenhouseResponse findById(@PathVariable Long id) {
        return greenhouseService.findById(id);
    }

    @GetMapping("/{id}/dashboard")
    public GreenhouseDashboardResponse getDashboard(@PathVariable Long id) {
        return greenhouseService.getDashboard(id);
    }

    @PostMapping
    public ResponseEntity<GreenhouseResponse> create(@Valid @RequestBody GreenhouseRequest request) {
        GreenhouseResponse response = greenhouseService.create(request);
        return ResponseEntity
                .created(URI.create("/api/greenhouses/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{id}")
    public GreenhouseResponse update(@PathVariable Long id, @Valid @RequestBody GreenhouseRequest request) {
        return greenhouseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        greenhouseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
