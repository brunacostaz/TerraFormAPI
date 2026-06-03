package br.com.fiap.terraform.dto;

import br.com.fiap.terraform.enums.ApplicationTarget;
import java.util.List;

public class ReactionResponse {

    private final String compoundCode;
    private final String displayName;
    private final String equation;
    private final String purpose;
    private final List<ReagentResponse> reagents;
    private final List<ApplicationTarget> availableTargets;

    public ReactionResponse(String compoundCode, String displayName, String equation, String purpose,
            List<ReagentResponse> reagents, List<ApplicationTarget> availableTargets) {
        this.compoundCode = compoundCode;
        this.displayName = displayName;
        this.equation = equation;
        this.purpose = purpose;
        this.reagents = reagents;
        this.availableTargets = availableTargets;
    }

    public String getCompoundCode() {
        return compoundCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEquation() {
        return equation;
    }

    public String getPurpose() {
        return purpose;
    }

    public List<ReagentResponse> getReagents() {
        return reagents;
    }

    public List<ApplicationTarget> getAvailableTargets() {
        return availableTargets;
    }
}

