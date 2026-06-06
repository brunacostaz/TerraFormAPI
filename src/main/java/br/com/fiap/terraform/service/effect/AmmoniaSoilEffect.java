package br.com.fiap.terraform.service.effect;

import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.enums.ApplicationTarget;
import br.com.fiap.terraform.service.AgricultureCalculationService;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class AmmoniaSoilEffect extends AbstractCompoundEffect {

    public AmmoniaSoilEffect(AgricultureCalculationService calculationService) {
        super("NH3", ApplicationTarget.SOIL, calculationService);
    }

    @Override
    protected CompoundEffectResult applyInternal(Greenhouse greenhouse, BigDecimal multiplier, boolean mutate) {
        BigDecimal totalChange = BigDecimal.ZERO;

        BigDecimal beforeNitrogen = greenhouse.getSoil().getNitrogen();
        BigDecimal afterNitrogen = addAndClamp(beforeNitrogen, BigDecimal.valueOf(15).multiply(multiplier), 0, 100);
        totalChange = totalChange.add(absDiff(beforeNitrogen, afterNitrogen));
        if (mutate) {
            greenhouse.getSoil().updateNitrogen(afterNitrogen);
        }

        BigDecimal beforePh = greenhouse.getSoil().getPh();
        BigDecimal afterPh = addAndClamp(beforePh, BigDecimal.valueOf(0.2).multiply(multiplier), 0, 14);
        totalChange = totalChange.add(absDiff(beforePh, afterPh));
        if (mutate) {
            greenhouse.getSoil().updatePh(afterPh);
        }

        return new CompoundEffectResult(totalChange);
    }
}
