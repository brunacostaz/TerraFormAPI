package br.com.fiap.terraform.service.effect;

import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.enums.ApplicationTarget;
import br.com.fiap.terraform.service.AgricultureCalculationService;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class WaterAirEffect extends AbstractCompoundEffect {

    public WaterAirEffect(AgricultureCalculationService calculationService) {
        super("H2O", ApplicationTarget.AIR, calculationService);
    }

    @Override
    protected CompoundEffectResult applyInternal(Greenhouse greenhouse, BigDecimal multiplier, boolean mutate) {
        BigDecimal totalChange = BigDecimal.ZERO;

        BigDecimal beforeHumidity = greenhouse.getAir().getHumidity();
        BigDecimal afterHumidity = addAndClamp(beforeHumidity, BigDecimal.valueOf(6).multiply(multiplier), 0, 100);
        totalChange = totalChange.add(absDiff(beforeHumidity, afterHumidity));
        if (mutate) {
            greenhouse.getAir().updateHumidity(afterHumidity);
        }

        BigDecimal beforeCo2 = greenhouse.getAir().getCarbonDioxide();
        BigDecimal afterCo2 = addAndClamp(beforeCo2, BigDecimal.valueOf(-0.05).multiply(multiplier), 0, 5);
        totalChange = totalChange.add(absDiff(beforeCo2, afterCo2));
        if (mutate) {
            greenhouse.getAir().updateCarbonDioxide(afterCo2);
        }

        return new CompoundEffectResult(totalChange);
    }
}
