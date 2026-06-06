package br.com.fiap.terraform.service.effect;

import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.enums.ApplicationTarget;
import br.com.fiap.terraform.service.AgricultureCalculationService;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class WaterSoilEffect extends AbstractCompoundEffect {

    public WaterSoilEffect(AgricultureCalculationService calculationService) {
        super("H2O", ApplicationTarget.SOIL, calculationService);
    }

    @Override
    protected CompoundEffectResult applyInternal(Greenhouse greenhouse, BigDecimal multiplier, boolean mutate) {
        BigDecimal before = greenhouse.getSoil().getHumidity();
        BigDecimal after = addAndClamp(before, BigDecimal.valueOf(12).multiply(multiplier), 0, 100);
        if (mutate) {
            greenhouse.getSoil().updateHumidity(after);
        }
        return new CompoundEffectResult(absDiff(before, after));
    }
}
