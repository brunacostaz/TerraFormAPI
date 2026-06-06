package br.com.fiap.terraform.service.effect;

import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.enums.ApplicationTarget;
import br.com.fiap.terraform.service.AgricultureCalculationService;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class CalciumCarbonateSoilEffect extends AbstractCompoundEffect {

    public CalciumCarbonateSoilEffect(AgricultureCalculationService calculationService) {
        super("CaCO3", ApplicationTarget.SOIL, calculationService);
    }

    @Override
    protected CompoundEffectResult applyInternal(Greenhouse greenhouse, BigDecimal multiplier, boolean mutate) {
        BigDecimal totalChange = BigDecimal.ZERO;

        BigDecimal beforeCalcium = greenhouse.getSoil().getCalcium();
        BigDecimal afterCalcium = addAndClamp(beforeCalcium, BigDecimal.valueOf(12).multiply(multiplier), 0, 100);
        totalChange = totalChange.add(absDiff(beforeCalcium, afterCalcium));
        if (mutate) {
            greenhouse.getSoil().updateCalcium(afterCalcium);
        }

        BigDecimal beforePh = greenhouse.getSoil().getPh();
        BigDecimal afterPh = addAndClamp(beforePh, BigDecimal.valueOf(0.3).multiply(multiplier), 0, 14);
        totalChange = totalChange.add(absDiff(beforePh, afterPh));
        if (mutate) {
            greenhouse.getSoil().updatePh(afterPh);
        }

        return new CompoundEffectResult(totalChange);
    }
}
