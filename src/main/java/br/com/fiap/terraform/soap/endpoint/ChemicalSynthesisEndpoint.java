package br.com.fiap.terraform.soap.endpoint;

import br.com.fiap.terraform.dto.ReactionResponse;
import br.com.fiap.terraform.dto.SynthesisResponse;
import br.com.fiap.terraform.soap.model.ConsultReactionRequest;
import br.com.fiap.terraform.soap.model.ConsultReactionResponse;
import br.com.fiap.terraform.soap.model.ProcessSynthesisRequest;
import br.com.fiap.terraform.soap.model.ProcessSynthesisResponse;
import br.com.fiap.terraform.soap.model.SoapReagent;
import br.com.fiap.terraform.service.ChemicalReactionService;
import br.com.fiap.terraform.service.SynthesisService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ChemicalSynthesisEndpoint {

    public static final String NAMESPACE = "http://terraform.fiap.com.br/soap/chemical-synthesis";

    private final ChemicalReactionService chemicalReactionService;
    private final SynthesisService synthesisService;

    public ChemicalSynthesisEndpoint(ChemicalReactionService chemicalReactionService,
            SynthesisService synthesisService) {
        this.chemicalReactionService = chemicalReactionService;
        this.synthesisService = synthesisService;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "consultReactionRequest")
    @ResponsePayload
    public ConsultReactionResponse consultReaction(@RequestPayload ConsultReactionRequest request) {
        ReactionResponse reaction = chemicalReactionService.findResponseByCompoundCode(request.getCompoundCode());

        ConsultReactionResponse response = new ConsultReactionResponse();
        response.setCompoundCode(reaction.getCompoundCode());
        response.setDisplayName(reaction.getDisplayName());
        response.setEquation(reaction.getEquation());
        response.setPurpose(reaction.getPurpose());
        response.setAvailableTargets(reaction.getAvailableTargets().stream().map(Enum::name).toList());
        response.setReagents(reaction.getReagents().stream().map(reagent -> {
            SoapReagent soapReagent = new SoapReagent();
            soapReagent.setType(reagent.getType().name());
            soapReagent.setCode(reagent.getCode());
            soapReagent.setCostPerUnit(reagent.getCostPerUnit());
            return soapReagent;
        }).toList());

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "processSynthesisRequest")
    @ResponsePayload
    public ProcessSynthesisResponse processSynthesis(@RequestPayload ProcessSynthesisRequest request) {
        SynthesisResponse synthesis = synthesisService.synthesize(
                request.getGreenhouseId(),
                request.getCompoundCode(),
                request.getUnits()
        );

        ProcessSynthesisResponse response = new ProcessSynthesisResponse();
        response.setSuccess(true);
        response.setGreenhouseId(synthesis.getGreenhouseId());
        response.setCompoundCode(synthesis.getCompoundCode());
        response.setUnits(synthesis.getUnits());
        response.setProducedPercentage(synthesis.getProducedPercentage());
        response.setMessage(synthesis.getMessage());
        return response;
    }
}

