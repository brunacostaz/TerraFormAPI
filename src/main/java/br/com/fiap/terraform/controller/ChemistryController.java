package br.com.fiap.terraform.controller;

import br.com.fiap.terraform.dto.ApplyCompoundRequest;
import br.com.fiap.terraform.dto.ApplyCompoundResponse;
import br.com.fiap.terraform.dto.SynthesisRequest;
import br.com.fiap.terraform.dto.SynthesisResponse;
import br.com.fiap.terraform.service.CompoundApplicationService;
import br.com.fiap.terraform.service.SynthesisService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greenhouses/{greenhouseId}")
public class ChemistryController {

    private final SynthesisService synthesisService;
    private final CompoundApplicationService compoundApplicationService;

    public ChemistryController(SynthesisService synthesisService,
            CompoundApplicationService compoundApplicationService) {
        this.synthesisService = synthesisService;
        this.compoundApplicationService = compoundApplicationService;
    }

    @PostMapping("/synthesis")
    public SynthesisResponse synthesize(@PathVariable Long greenhouseId, @Valid @RequestBody SynthesisRequest request) {
        return synthesisService.synthesize(greenhouseId, request.getCompoundCode(), request.getUnits());
    }

    @PostMapping("/compounds/apply")
    public ApplyCompoundResponse applyCompound(@PathVariable Long greenhouseId,
            @Valid @RequestBody ApplyCompoundRequest request) {
        return compoundApplicationService.apply(
                greenhouseId,
                request.getCompoundCode(),
                request.getTarget(),
                request.getQuantity()
        );
    }
}

