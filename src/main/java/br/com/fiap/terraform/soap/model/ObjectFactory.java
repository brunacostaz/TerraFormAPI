package br.com.fiap.terraform.soap.model;

import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ConsultReactionRequest createConsultReactionRequest() {
        return new ConsultReactionRequest();
    }

    public ConsultReactionResponse createConsultReactionResponse() {
        return new ConsultReactionResponse();
    }

    public ProcessSynthesisRequest createProcessSynthesisRequest() {
        return new ProcessSynthesisRequest();
    }

    public ProcessSynthesisResponse createProcessSynthesisResponse() {
        return new ProcessSynthesisResponse();
    }

    public SoapReagent createSoapReagent() {
        return new SoapReagent();
    }
}

