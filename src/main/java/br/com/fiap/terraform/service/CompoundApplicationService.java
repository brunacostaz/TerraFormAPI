package br.com.fiap.terraform.service;

import br.com.fiap.terraform.dto.ApplyCompoundResponse;
import br.com.fiap.terraform.dto.BatchApplyCompoundItemRequest;
import br.com.fiap.terraform.entity.AirState;
import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.entity.OperationLog;
import br.com.fiap.terraform.entity.SoilState;
import br.com.fiap.terraform.enums.ApplicationTarget;
import br.com.fiap.terraform.enums.LogType;
import br.com.fiap.terraform.exception.InsufficientStockException;
import br.com.fiap.terraform.repository.OperationLogRepository;
import br.com.fiap.terraform.service.effect.CompoundEffect;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CompoundApplicationService {

    private final GreenhouseService greenhouseService;
    private final ChemicalReactionService chemicalReactionService;
    private final InventoryService inventoryService;
    private final AgricultureCalculationService calculationService;
    private final OperationLogRepository operationLogRepository;
    private final List<CompoundEffect> compoundEffects;

    public CompoundApplicationService(GreenhouseService greenhouseService,
            ChemicalReactionService chemicalReactionService,
            InventoryService inventoryService,
            AgricultureCalculationService calculationService,
            OperationLogRepository operationLogRepository,
            List<CompoundEffect> compoundEffects) {
        this.greenhouseService = greenhouseService;
        this.chemicalReactionService = chemicalReactionService;
        this.inventoryService = inventoryService;
        this.calculationService = calculationService;
        this.operationLogRepository = operationLogRepository;
        this.compoundEffects = compoundEffects;
    }

    @Transactional
    public ApplyCompoundResponse apply(Long greenhouseId, String compoundCode, ApplicationTarget target,
            BigDecimal requestedQuantity) {
        Greenhouse greenhouse = greenhouseService.findEntityById(greenhouseId);
        ChemicalReaction reaction = chemicalReactionService.findByCompoundCode(compoundCode);
        String normalizedCompound = reaction.getCompoundCode();

        validateTarget(reaction, target);
        validateAvailableStock(greenhouseId, normalizedCompound, requestedQuantity);

        CompoundEffect effect = findEffect(normalizedCompound, target);
        BigDecimal multiplier = toMultiplier(requestedQuantity);
        if (effect.preview(greenhouse, multiplier).totalChange().compareTo(BigDecimal.ZERO) <= 0) {
            return new ApplyCompoundResponse(
                    "A aplicacao nao alteraria o estado da estufa; estoque preservado.",
                    greenhouseService.getDashboard(greenhouseId)
            );
        }

        effect.apply(greenhouse, multiplier);
        recalculateQualities(greenhouse);
        inventoryService.debit(greenhouseId, normalizedCompound, requestedQuantity);

        operationLogRepository.save(new OperationLog(
                greenhouse,
                LogType.APPLICATION,
                null,
                "Aplicacao de " + requestedQuantity + "% de " + normalizedCompound + " em " + target.getLabel() + "."
        ));

        return new ApplyCompoundResponse(
                "Composto aplicado com sucesso.",
                greenhouseService.getDashboard(greenhouseId)
        );
    }

    @Transactional
    public ApplyCompoundResponse applyBatch(Long greenhouseId, List<BatchApplyCompoundItemRequest> applications) {
        Greenhouse greenhouse = greenhouseService.findEntityById(greenhouseId);
        Map<String, BigDecimal> requestedByCompound = new LinkedHashMap<>();

        for (BatchApplyCompoundItemRequest application : applications) {
            ChemicalReaction reaction = chemicalReactionService.findByCompoundCode(application.getCompoundCode());
            String normalizedCompound = reaction.getCompoundCode();
            ApplicationTarget target = application.getTarget();

            validateTarget(reaction, target);
            CompoundEffect effect = findEffect(normalizedCompound, target);

            BigDecimal multiplier = toMultiplier(application.getQuantity());
            if (effect.preview(greenhouse, multiplier).totalChange().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(
                        "A aplicacao de " + normalizedCompound + " em " + target.getLabel()
                                + " nao alteraria o estado da estufa."
                );
            }

            requestedByCompound.merge(normalizedCompound, application.getQuantity(), BigDecimal::add);
        }

        requestedByCompound.forEach((compoundCode, totalQuantity) ->
                validateAvailableStock(greenhouseId, compoundCode, totalQuantity)
        );

        for (BatchApplyCompoundItemRequest application : applications) {
            ChemicalReaction reaction = chemicalReactionService.findByCompoundCode(application.getCompoundCode());
            findEffect(reaction.getCompoundCode(), application.getTarget())
                    .apply(greenhouse, toMultiplier(application.getQuantity()));
        }

        recalculateQualities(greenhouse);
        requestedByCompound.forEach((compoundCode, totalQuantity) ->
                inventoryService.debit(greenhouseId, compoundCode, totalQuantity)
        );

        operationLogRepository.save(new OperationLog(
                greenhouse,
                LogType.APPLICATION,
                null,
                "Nutricao em lote concluida com " + applications.size() + " aplicacao(oes)."
        ));

        return new ApplyCompoundResponse(
                "Nutricao em lote concluida com sucesso.",
                greenhouseService.getDashboard(greenhouseId)
        );
    }

    private void validateTarget(ChemicalReaction reaction, ApplicationTarget target) {
        if (!reaction.getAvailableTargets().contains(target)) {
            throw new IllegalArgumentException(
                    reaction.getCompoundCode() + " nao pode ser aplicado em " + target.getLabel() + "."
            );
        }
    }

    private void validateAvailableStock(Long greenhouseId, String compoundCode, BigDecimal requestedQuantity) {
        BigDecimal available = inventoryService.getAvailable(greenhouseId, compoundCode);
        if (available.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Nao ha estoque disponivel de " + compoundCode + ".");
        }
        if (available.compareTo(requestedQuantity) < 0) {
            throw new InsufficientStockException(
                    "Estoque insuficiente de " + compoundCode + ". Disponivel: "
                            + available + "%, solicitado: " + requestedQuantity + "%."
            );
        }
    }

    private CompoundEffect findEffect(String compoundCode, ApplicationTarget target) {
        return compoundEffects.stream()
                .filter(effect -> effect.supports(compoundCode, target))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nao ha regra de aplicacao para " + compoundCode + " em " + target.getLabel() + "."
                ));
    }

    private BigDecimal toMultiplier(BigDecimal quantity) {
        return quantity.divide(BigDecimal.TEN, 4, RoundingMode.HALF_UP);
    }

    private void recalculateQualities(Greenhouse greenhouse) {
        SoilState soil = greenhouse.getSoil();
        AirState air = greenhouse.getAir();
        soil.updateQuality(calculationService.calculateSoilQuality(soil));
        air.updateQuality(calculationService.calculateAirQuality(air));
    }
}
