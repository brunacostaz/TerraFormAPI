package br.com.fiap.terraform.service.effect;

import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.enums.ApplicationTarget;
import java.math.BigDecimal;

public interface CompoundEffect {

    boolean supports(String compoundCode, ApplicationTarget target);

    CompoundEffectResult preview(Greenhouse greenhouse, BigDecimal multiplier);

    CompoundEffectResult apply(Greenhouse greenhouse, BigDecimal multiplier);
}
