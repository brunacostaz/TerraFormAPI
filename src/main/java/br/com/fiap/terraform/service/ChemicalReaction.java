package br.com.fiap.terraform.service;

import br.com.fiap.terraform.enums.ApplicationTarget;
import br.com.fiap.terraform.enums.ReagentType;
import java.math.BigDecimal;
import java.util.List;

public class ChemicalReaction {

    private final String compoundCode;
    private final String displayName;
    private final String equation;
    private final String purpose;
    private final List<Reagent> reagents;
    private final List<ApplicationTarget> availableTargets;

    public ChemicalReaction(String compoundCode, String displayName, String equation, String purpose,
            List<Reagent> reagents, List<ApplicationTarget> availableTargets) {
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

    public List<Reagent> getReagents() {
        return reagents;
    }

    public List<ApplicationTarget> getAvailableTargets() {
        return availableTargets;
    }

    public static class Reagent {

        private final ReagentType type;
        private final String code;
        private final BigDecimal costPerUnit;

        public Reagent(ReagentType type, String code, String costPerUnit) {
            this.type = type;
            this.code = code;
            this.costPerUnit = new BigDecimal(costPerUnit);
        }

        public ReagentType getType() {
            return type;
        }

        public String getCode() {
            return code;
        }

        public BigDecimal getCostPerUnit() {
            return costPerUnit;
        }
    }
}

