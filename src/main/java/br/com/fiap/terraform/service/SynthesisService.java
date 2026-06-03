package br.com.fiap.terraform.service;

import br.com.fiap.terraform.dto.SynthesisResponse;
import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.entity.OperationLog;
import br.com.fiap.terraform.enums.LogType;
import br.com.fiap.terraform.repository.OperationLogRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class SynthesisService {

    private final GreenhouseService greenhouseService;
    private final ChemicalReactionService chemicalReactionService;
    private final InventoryService inventoryService;
    private final OperationLogRepository operationLogRepository;

    public SynthesisService(GreenhouseService greenhouseService,
            ChemicalReactionService chemicalReactionService,
            InventoryService inventoryService,
            OperationLogRepository operationLogRepository) {
        this.greenhouseService = greenhouseService;
        this.chemicalReactionService = chemicalReactionService;
        this.inventoryService = inventoryService;
        this.operationLogRepository = operationLogRepository;
    }

    @Transactional
    public SynthesisResponse synthesize(Long greenhouseId, String compoundCode, int units) {
        if (units <= 0) {
            throw new IllegalArgumentException("A quantidade de unidades deve ser maior que zero.");
        }

        Greenhouse greenhouse = greenhouseService.findEntityById(greenhouseId);
        ChemicalReaction reaction = chemicalReactionService.findByCompoundCode(compoundCode);

        inventoryService.validateReagentStock(greenhouseId, reaction, units);
        reaction.getReagents().forEach(reagent -> inventoryService.debitReagent(greenhouseId, reagent, units));

        BigDecimal produced = BigDecimal.valueOf(units).multiply(BigDecimal.TEN);
        inventoryService.credit(greenhouseId, reaction.getCompoundCode(), produced);

        operationLogRepository.save(new OperationLog(
                greenhouse,
                LogType.SYNTHESIS,
                null,
                "Sintese de " + produced + "% de " + reaction.getCompoundCode() + " concluida."
        ));

        return new SynthesisResponse(
                greenhouseId,
                reaction.getCompoundCode(),
                units,
                produced,
                "Sintese concluida com sucesso.",
                inventoryService.findByGreenhouseId(greenhouseId)
        );
    }
}

