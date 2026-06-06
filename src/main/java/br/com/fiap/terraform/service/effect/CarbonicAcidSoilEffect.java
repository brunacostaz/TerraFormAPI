package br.com.fiap.terraform.service.effect;

import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.enums.ApplicationTarget;
import br.com.fiap.terraform.service.AgricultureCalculationService;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class CarbonicAcidSoilEffect extends AbstractCompoundEffect {

    public CarbonicAcidSoilEffect(AgricultureCalculationService calculationService) {
        super("H2CO3", ApplicationTarget.SOIL, calculationService);
    }

    @Override
    protected CompoundEffectResult applyInternal(Greenhouse greenhouse, BigDecimal multiplier, boolean mutate) {
        BigDecimal totalChange = BigDecimal.ZERO;

        BigDecimal beforePh = greenhouse.getSoil().getPh();
        BigDecimal afterPh = addAndClamp(beforePh, BigDecimal.valueOf(-0.4).multiply(multiplier), 0, 14);
        totalChange = totalChange.add(absDiff(beforePh, afterPh));
        if (mutate) {
            greenhouse.getSoil().updatePh(afterPh);
        }

        BigDecimal beforeHumidity = greenhouse.getSoil().getHumidity();
        BigDecimal afterHumidity = addAndClamp(beforeHumidity, BigDecimal.valueOf(4).multiply(multiplier), 0, 100);
        totalChange = totalChange.add(absDiff(beforeHumidity, afterHumidity));
        if (mutate) {
            greenhouse.getSoil().updateHumidity(afterHumidity);
        }

        return new CompoundEffectResult(totalChange);
    }
}
