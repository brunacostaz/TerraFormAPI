package br.com.fiap.terraform.service;

import br.com.fiap.terraform.dto.InventoryItemResponse;
import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.entity.InventoryItem;
import br.com.fiap.terraform.entity.OperationLog;
import br.com.fiap.terraform.enums.LogType;
import br.com.fiap.terraform.enums.ReagentType;
import br.com.fiap.terraform.exception.InsufficientStockException;
import br.com.fiap.terraform.exception.ResourceNotFoundException;
import br.com.fiap.terraform.repository.InventoryItemRepository;
import br.com.fiap.terraform.repository.OperationLogRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final InventoryItemRepository inventoryItemRepository;
    private final TerraFormMapper mapper;
    private final GreenhouseService greenhouseService;
    private final OperationLogRepository operationLogRepository;

    public InventoryService(InventoryItemRepository inventoryItemRepository, TerraFormMapper mapper,
            GreenhouseService greenhouseService, OperationLogRepository operationLogRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.mapper = mapper;
        this.greenhouseService = greenhouseService;
        this.operationLogRepository = operationLogRepository;
    }

    public List<InventoryItemResponse> findByGreenhouseId(Long greenhouseId) {
        return inventoryItemRepository.findByGreenhouseIdOrderByResourceTypeAscResourceCodeAsc(greenhouseId)
                .stream()
                .map(mapper::toInventoryItemResponse)
                .toList();
    }

    public BigDecimal getAvailable(Long greenhouseId, String resourceCode) {
        return findEntity(greenhouseId, resourceCode).getPercentage();
    }

    public void debit(Long greenhouseId, String resourceCode, BigDecimal quantity) {
        InventoryItem item = findEntity(greenhouseId, resourceCode);
        if (item.getPercentage().compareTo(quantity) < 0) {
            throw new InsufficientStockException("Estoque insuficiente de " + resourceCode + ".");
        }
        item.changePercentage(clamp(item.getPercentage().subtract(quantity)));
    }

    public void credit(Long greenhouseId, String resourceCode, BigDecimal quantity) {
        InventoryItem item = findEntity(greenhouseId, resourceCode);
        item.changePercentage(clamp(item.getPercentage().add(quantity)));
    }

    @Transactional
    public List<InventoryItemResponse> restock(Long greenhouseId, String resourceCode, BigDecimal quantity) {
        Greenhouse greenhouse = greenhouseService.findEntityById(greenhouseId);
        InventoryItem item = findEntity(greenhouseId, resourceCode);
        BigDecimal before = item.getPercentage();

        item.changePercentage(clamp(before.add(quantity)));
        BigDecimal credited = item.getPercentage().subtract(before);

        operationLogRepository.save(new OperationLog(
                greenhouse,
                LogType.REFILL,
                null,
                "Reposição de " + credited + "% de " + item.getResourceCode() + " registrada."
        ));

        return findByGreenhouseId(greenhouseId);
    }

    public void validateReagentStock(Long greenhouseId, ChemicalReaction reaction, int units) {
        for (ChemicalReaction.Reagent reagent : reaction.getReagents()) {
            BigDecimal cost = reagent.getCostPerUnit().multiply(BigDecimal.valueOf(units));
            BigDecimal available = getAvailable(greenhouseId, reagent.getCode());
            if (available.compareTo(cost) < 0) {
                throw new InsufficientStockException(
                        "Estoque insuficiente de " + reagent.getCode() + " para sintetizar "
                                + reaction.getCompoundCode() + "."
                );
            }
        }
    }

    public int calculateMaxUnits(Long greenhouseId, ChemicalReaction reaction) {
        int maxUnits = 20;
        for (ChemicalReaction.Reagent reagent : reaction.getReagents()) {
            BigDecimal available = getAvailable(greenhouseId, reagent.getCode());
            int maxByThisReagent = available.divide(reagent.getCostPerUnit(), 0, RoundingMode.DOWN).intValue();
            maxUnits = Math.min(maxUnits, maxByThisReagent);
        }
        return Math.max(0, maxUnits);
    }

    public void debitReagent(Long greenhouseId, ChemicalReaction.Reagent reagent, int units) {
        BigDecimal cost = reagent.getCostPerUnit().multiply(BigDecimal.valueOf(units));
        debit(greenhouseId, reagent.getCode(), cost);
    }

    public boolean isCompound(ReagentType type) {
        return type == ReagentType.SYNTHESIZED_COMPOUND;
    }

    private InventoryItem findEntity(Long greenhouseId, String resourceCode) {
        return inventoryItemRepository.findByGreenhouseIdAndResourceCode(greenhouseId, resourceCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item de estoque nao encontrado: " + resourceCode + " na estufa " + greenhouseId
                ));
    }

    private BigDecimal clamp(BigDecimal value) {
        double clamped = Math.max(0, Math.min(100, value.doubleValue()));
        return BigDecimal.valueOf(clamped).setScale(2, RoundingMode.HALF_UP);
    }
}
