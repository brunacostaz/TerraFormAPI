package br.com.fiap.terraform.service;

import br.com.fiap.terraform.dto.SynthesisResponse;
import br.com.fiap.terraform.exception.InsufficientStockException;
import br.com.fiap.terraform.exception.ResourceNotFoundException;
import br.com.fiap.terraform.soap.client.ChemicalSynthesisSoapClient;
import br.com.fiap.terraform.soap.model.ProcessSynthesisResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SynthesisApplicationService {

    private final ChemicalSynthesisSoapClient chemicalSynthesisSoapClient;
    private final InventoryService inventoryService;

    public SynthesisApplicationService(ChemicalSynthesisSoapClient chemicalSynthesisSoapClient,
            InventoryService inventoryService) {
        this.chemicalSynthesisSoapClient = chemicalSynthesisSoapClient;
        this.inventoryService = inventoryService;
    }

    public SynthesisResponse synthesizeThroughSoap(Long greenhouseId, String compoundCode, int units) {
        ProcessSynthesisResponse soapResponse = chemicalSynthesisSoapClient.processSynthesis(
                greenhouseId,
                compoundCode,
                units
        );

        if (!soapResponse.isSuccess()) {
            throwBusinessException(soapResponse);
        }

        return new SynthesisResponse(
                soapResponse.getGreenhouseId(),
                soapResponse.getCompoundCode(),
                soapResponse.getUnits(),
                soapResponse.getProducedPercentage(),
                soapResponse.getMessage(),
                inventoryService.findByGreenhouseId(greenhouseId)
        );
    }

    private void throwBusinessException(ProcessSynthesisResponse soapResponse) {
        if ("ESTOQUE_INSUFICIENTE".equals(soapResponse.getErrorCode())) {
            throw new InsufficientStockException(soapResponse.getMessage());
        }
        if ("RECURSO_NAO_ENCONTRADO".equals(soapResponse.getErrorCode())) {
            throw new ResourceNotFoundException(soapResponse.getMessage());
        }
        throw new IllegalArgumentException(soapResponse.getMessage());
    }
}
