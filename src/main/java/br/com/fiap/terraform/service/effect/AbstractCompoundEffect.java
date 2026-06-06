package br.com.fiap.terraform.service.effect;

import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.enums.ApplicationTarget;
import br.com.fiap.terraform.service.AgricultureCalculationService;
import java.math.BigDecimal;

public abstract class AbstractCompoundEffect implements CompoundEffect {

    private final String compoundCode;
    private final ApplicationTarget target;
    protected final AgricultureCalculationService calculationService;

    protected AbstractCompoundEffect(String compoundCode, ApplicationTarget target,
            AgricultureCalculationService calculationService) {
        this.compoundCode = compoundCode;
        this.target = target;
        this.calculationService = calculationService;
    }

    @Override
    public boolean supports(String compoundCode, ApplicationTarget target) {
        return this.compoundCode.equalsIgnoreCase(compoundCode) && this.target == target;
    }

    @Override
    public CompoundEffectResult preview(Greenhouse greenhouse, BigDecimal multiplier) {
        return applyInternal(greenhouse, multiplier, false);
    }

    @Override
    public CompoundEffectResult apply(Greenhouse greenhouse, BigDecimal multiplier) {
        return applyInternal(greenhouse, multiplier, true);
    }

    protected abstract CompoundEffectResult applyInternal(Greenhouse greenhouse, BigDecimal multiplier, boolean mutate);

    protected BigDecimal addAndClamp(BigDecimal current, BigDecimal delta, double min, double max) {
        return calculationService.clamp(current.add(delta), min, max);
    }

    protected BigDecimal absDiff(BigDecimal before, BigDecimal after) {
        return after.subtract(before).abs();
    }
}
