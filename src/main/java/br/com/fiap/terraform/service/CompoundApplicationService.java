package br.com.fiap.terraform.service;

import br.com.fiap.terraform.dto.ApplyCompoundResponse;
import br.com.fiap.terraform.entity.AirState;
import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.entity.OperationLog;
import br.com.fiap.terraform.entity.SoilState;
import br.com.fiap.terraform.enums.ApplicationTarget;
import br.com.fiap.terraform.enums.LogType;
import br.com.fiap.terraform.repository.OperationLogRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class CompoundApplicationService {

    private final GreenhouseService greenhouseService;
    private final ChemicalReactionService chemicalReactionService;
    private final InventoryService inventoryService;
    private final AgricultureCalculationService calculationService;
    private final OperationLogRepository operationLogRepository;

    public CompoundApplicationService(GreenhouseService greenhouseService,
            ChemicalReactionService chemicalReactionService,
            InventoryService inventoryService,
            AgricultureCalculationService calculationService,
            OperationLogRepository operationLogRepository) {
        this.greenhouseService = greenhouseService;
        this.chemicalReactionService = chemicalReactionService;
        this.inventoryService = inventoryService;
        this.calculationService = calculationService;
        this.operationLogRepository = operationLogRepository;
    }

    @Transactional
    public ApplyCompoundResponse apply(Long greenhouseId, String compoundCode, ApplicationTarget target,
            BigDecimal requestedQuantity) {
        Greenhouse greenhouse = greenhouseService.findEntityById(greenhouseId);
        ChemicalReaction reaction = chemicalReactionService.findByCompoundCode(compoundCode);
        String normalizedCompound = reaction.getCompoundCode();

        if (!reaction.getAvailableTargets().contains(target)) {
            throw new IllegalArgumentException(normalizedCompound + " nao pode ser aplicado em " + target + ".");
        }

        BigDecimal available = inventoryService.getAvailable(greenhouseId, normalizedCompound);
        BigDecimal realQuantity = requestedQuantity.min(available);
        if (realQuantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Nao ha estoque disponivel de " + normalizedCompound + ".");
        }

        BigDecimal multiplier = realQuantity.divide(BigDecimal.TEN, 4, RoundingMode.HALF_UP);
        BigDecimal totalChange = applyEffects(greenhouse, normalizedCompound, target, multiplier, false);
        if (totalChange.compareTo(BigDecimal.ZERO) <= 0) {
            return new ApplyCompoundResponse(
                    "A aplicacao nao alteraria o estado da estufa; estoque preservado.",
                    greenhouseService.getDashboard(greenhouseId)
            );
        }

        applyEffects(greenhouse, normalizedCompound, target, multiplier, true);
        recalculateQualities(greenhouse);
        inventoryService.debit(greenhouseId, normalizedCompound, realQuantity);

        operationLogRepository.save(new OperationLog(
                greenhouse,
                LogType.APPLICATION,
                null,
                "Aplicacao de " + realQuantity + "% de " + normalizedCompound + " em " + target + "."
        ));

        return new ApplyCompoundResponse(
                "Composto aplicado com sucesso.",
                greenhouseService.getDashboard(greenhouseId)
        );
    }

    private BigDecimal applyEffects(Greenhouse greenhouse, String compoundCode, ApplicationTarget target,
            BigDecimal multiplier, boolean mutate) {
        BigDecimal before;
        BigDecimal after;
        BigDecimal totalChange = BigDecimal.ZERO;

        if ("H2O".equals(compoundCode) && target == ApplicationTarget.SOIL) {
            before = greenhouse.getSoil().getHumidity();
            after = addAndClamp(before, BigDecimal.valueOf(12).multiply(multiplier), 0, 100);
            totalChange = totalChange.add(absDiff(before, after));
            if (mutate) {
                greenhouse.getSoil().updateHumidity(after);
            }
        }

        if ("H2O".equals(compoundCode) && target == ApplicationTarget.AIR) {
            before = greenhouse.getAir().getHumidity();
            after = addAndClamp(before, BigDecimal.valueOf(6).multiply(multiplier), 0, 100);
            totalChange = totalChange.add(absDiff(before, after));
            if (mutate) {
                greenhouse.getAir().updateHumidity(after);
            }

            before = greenhouse.getAir().getCarbonDioxide();
            after = addAndClamp(before, BigDecimal.valueOf(-0.05).multiply(multiplier), 0, 5);
            totalChange = totalChange.add(absDiff(before, after));
            if (mutate) {
                greenhouse.getAir().updateCarbonDioxide(after);
            }
        }

        if ("NH3".equals(compoundCode) && target == ApplicationTarget.SOIL) {
            before = greenhouse.getSoil().getNitrogen();
            after = addAndClamp(before, BigDecimal.valueOf(15).multiply(multiplier), 0, 100);
            totalChange = totalChange.add(absDiff(before, after));
            if (mutate) {
                greenhouse.getSoil().updateNitrogen(after);
            }

            before = greenhouse.getSoil().getPh();
            after = addAndClamp(before, BigDecimal.valueOf(0.2).multiply(multiplier), 0, 14);
            totalChange = totalChange.add(absDiff(before, after));
            if (mutate) {
                greenhouse.getSoil().updatePh(after);
            }
        }

        if ("CaCO3".equals(compoundCode) && target == ApplicationTarget.SOIL) {
            before = greenhouse.getSoil().getCalcium();
            after = addAndClamp(before, BigDecimal.valueOf(12).multiply(multiplier), 0, 100);
            totalChange = totalChange.add(absDiff(before, after));
            if (mutate) {
                greenhouse.getSoil().updateCalcium(after);
            }

            before = greenhouse.getSoil().getPh();
            after = addAndClamp(before, BigDecimal.valueOf(0.3).multiply(multiplier), 0, 14);
            totalChange = totalChange.add(absDiff(before, after));
            if (mutate) {
                greenhouse.getSoil().updatePh(after);
            }
        }

        if ("H2CO3".equals(compoundCode) && target == ApplicationTarget.SOIL) {
            before = greenhouse.getSoil().getPh();
            after = addAndClamp(before, BigDecimal.valueOf(-0.4).multiply(multiplier), 0, 14);
            totalChange = totalChange.add(absDiff(before, after));
            if (mutate) {
                greenhouse.getSoil().updatePh(after);
            }

            before = greenhouse.getSoil().getHumidity();
            after = addAndClamp(before, BigDecimal.valueOf(4).multiply(multiplier), 0, 100);
            totalChange = totalChange.add(absDiff(before, after));
            if (mutate) {
                greenhouse.getSoil().updateHumidity(after);
            }
        }

        return totalChange;
    }

    private void recalculateQualities(Greenhouse greenhouse) {
        SoilState soil = greenhouse.getSoil();
        AirState air = greenhouse.getAir();
        soil.updateQuality(calculationService.calculateSoilQuality(soil));
        air.updateQuality(calculationService.calculateAirQuality(air));
    }

    private BigDecimal addAndClamp(BigDecimal current, BigDecimal delta, double min, double max) {
        return calculationService.clamp(current.add(delta), min, max);
    }

    private BigDecimal absDiff(BigDecimal before, BigDecimal after) {
        return after.subtract(before).abs();
    }
}

