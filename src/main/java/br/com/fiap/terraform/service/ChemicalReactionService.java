package br.com.fiap.terraform.service;

import br.com.fiap.terraform.dto.ReactionResponse;
import br.com.fiap.terraform.dto.ReagentResponse;
import br.com.fiap.terraform.enums.ApplicationTarget;
import br.com.fiap.terraform.enums.ReagentType;
import br.com.fiap.terraform.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChemicalReactionService {

    private final List<ChemicalReaction> reactions = List.of(
            new ChemicalReaction(
                    "H2O",
                    "Agua",
                    "2H2 + O2 -> 2H2O",
                    "Irrigacao do solo e vaporizacao de umidade no ar.",
                    List.of(
                            new ChemicalReaction.Reagent(ReagentType.RAW_ELEMENT, "H", "8"),
                            new ChemicalReaction.Reagent(ReagentType.RAW_ELEMENT, "O", "4")
                    ),
                    List.of(ApplicationTarget.SOIL, ApplicationTarget.AIR)
            ),
            new ChemicalReaction(
                    "NH3",
                    "Amonia",
                    "N2 + 3H2 -> 2NH3",
                    "Reposicao de nitrogenio e leve aumento de pH no solo.",
                    List.of(
                            new ChemicalReaction.Reagent(ReagentType.RAW_ELEMENT, "N", "4"),
                            new ChemicalReaction.Reagent(ReagentType.RAW_ELEMENT, "H", "12")
                    ),
                    List.of(ApplicationTarget.SOIL)
            ),
            new ChemicalReaction(
                    "CaCO3",
                    "Carbonato de calcio",
                    "Ca + CO2 + 1/2O2 -> CaCO3",
                    "Reposicao de calcio e correcao alcalina do pH.",
                    List.of(
                            new ChemicalReaction.Reagent(ReagentType.RAW_ELEMENT, "Ca", "5"),
                            new ChemicalReaction.Reagent(ReagentType.RAW_ELEMENT, "C", "5"),
                            new ChemicalReaction.Reagent(ReagentType.RAW_ELEMENT, "O", "8")
                    ),
                    List.of(ApplicationTarget.SOIL)
            ),
            new ChemicalReaction(
                    "H2CO3",
                    "Acido carbonico",
                    "CO2 + H2O -> H2CO3",
                    "Reducao controlada do pH do solo.",
                    List.of(
                            new ChemicalReaction.Reagent(ReagentType.RAW_ELEMENT, "C", "4"),
                            new ChemicalReaction.Reagent(ReagentType.RAW_ELEMENT, "O", "6"),
                            new ChemicalReaction.Reagent(ReagentType.SYNTHESIZED_COMPOUND, "H2O", "6")
                    ),
                    List.of(ApplicationTarget.SOIL)
            )
    );

    public List<ReactionResponse> findAll() {
        return reactions.stream().map(this::toResponse).toList();
    }

    public ReactionResponse findResponseByCompoundCode(String compoundCode) {
        return toResponse(findByCompoundCode(compoundCode));
    }

    public ChemicalReaction findByCompoundCode(String compoundCode) {
        return reactions.stream()
                .filter(reaction -> reaction.getCompoundCode().equalsIgnoreCase(compoundCode))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Reacao nao encontrada: " + compoundCode));
    }

    private ReactionResponse toResponse(ChemicalReaction reaction) {
        return new ReactionResponse(
                reaction.getCompoundCode(),
                reaction.getDisplayName(),
                reaction.getEquation(),
                reaction.getPurpose(),
                reaction.getReagents()
                        .stream()
                        .map(reagent -> new ReagentResponse(
                                reagent.getType(),
                                reagent.getCode(),
                                reagent.getCostPerUnit()
                        ))
                        .toList(),
                reaction.getAvailableTargets()
        );
    }
}

