package br.com.fiap.terraform.service;

import br.com.fiap.terraform.dto.SynthesisResponse;
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

        return new SynthesisResponse(
                soapResponse.getGreenhouseId(),
                soapResponse.getCompoundCode(),
                soapResponse.getUnits(),
                soapResponse.getProducedPercentage(),
                soapResponse.getMessage(),
                inventoryService.findByGreenhouseId(greenhouseId)
        );
    }
}

