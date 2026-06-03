package br.com.fiap.terraform.controller;

import br.com.fiap.terraform.dto.ReactionResponse;
import br.com.fiap.terraform.service.ChemicalReactionService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {

    private final ChemicalReactionService chemicalReactionService;

    public ReactionController(ChemicalReactionService chemicalReactionService) {
        this.chemicalReactionService = chemicalReactionService;
    }

    @GetMapping
    public List<ReactionResponse> findAll() {
        return chemicalReactionService.findAll();
    }

    @GetMapping("/{compoundCode}")
    public ReactionResponse findByCompoundCode(@PathVariable String compoundCode) {
        return chemicalReactionService.findResponseByCompoundCode(compoundCode);
    }
}

