package br.com.fiap.terraform.soap.client;

import br.com.fiap.terraform.soap.model.ConsultReactionRequest;
import br.com.fiap.terraform.soap.model.ConsultReactionResponse;
import br.com.fiap.terraform.soap.model.ProcessSynthesisRequest;
import br.com.fiap.terraform.soap.model.ProcessSynthesisResponse;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
public class ChemicalSynthesisSoapClient {

    private final WebServiceTemplate chemicalSynthesisWebServiceTemplate;

    public ChemicalSynthesisSoapClient(WebServiceTemplate chemicalSynthesisWebServiceTemplate) {
        this.chemicalSynthesisWebServiceTemplate = chemicalSynthesisWebServiceTemplate;
    }

    public ConsultReactionResponse consultReaction(String compoundCode) {
        ConsultReactionRequest request = new ConsultReactionRequest();
        request.setCompoundCode(compoundCode);
        return (ConsultReactionResponse) chemicalSynthesisWebServiceTemplate.marshalSendAndReceive(request);
    }

    public ProcessSynthesisResponse processSynthesis(Long greenhouseId, String compoundCode, int units) {
        ProcessSynthesisRequest request = new ProcessSynthesisRequest();
        request.setGreenhouseId(greenhouseId);
        request.setCompoundCode(compoundCode);
        request.setUnits(units);
        return (ProcessSynthesisResponse) chemicalSynthesisWebServiceTemplate.marshalSendAndReceive(request);
    }
}

