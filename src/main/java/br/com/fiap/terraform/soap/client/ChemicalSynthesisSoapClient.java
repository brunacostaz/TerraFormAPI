package br.com.fiap.terraform.soap.client;

import br.com.fiap.terraform.soap.model.ConsultReactionRequest;
import br.com.fiap.terraform.soap.model.ConsultReactionResponse;
import br.com.fiap.terraform.soap.model.ProcessSynthesisRequest;
import br.com.fiap.terraform.soap.model.ProcessSynthesisResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpUrlConnection;

@Component
public class ChemicalSynthesisSoapClient {

    private final WebServiceTemplate chemicalSynthesisWebServiceTemplate;
    private final String authorizationHeader;

    public ChemicalSynthesisSoapClient(WebServiceTemplate chemicalSynthesisWebServiceTemplate,
            @Value("${terraform.soap.client-user:operator}") String username,
            @Value("${terraform.soap.client-password:terraform-operator}") String password) {
        this.chemicalSynthesisWebServiceTemplate = chemicalSynthesisWebServiceTemplate;
        String credentials = username + ":" + password;
        this.authorizationHeader = "Basic " + Base64.getEncoder()
                .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }

    public ConsultReactionResponse consultReaction(String compoundCode) {
        ConsultReactionRequest request = new ConsultReactionRequest();
        request.setCompoundCode(compoundCode);
        return (ConsultReactionResponse) chemicalSynthesisWebServiceTemplate
                .marshalSendAndReceive(request, this::addAuthorizationHeader);
    }

    public ProcessSynthesisResponse processSynthesis(Long greenhouseId, String compoundCode, int units) {
        ProcessSynthesisRequest request = new ProcessSynthesisRequest();
        request.setGreenhouseId(greenhouseId);
        request.setCompoundCode(compoundCode);
        request.setUnits(units);
        return (ProcessSynthesisResponse) chemicalSynthesisWebServiceTemplate
                .marshalSendAndReceive(request, this::addAuthorizationHeader);
    }

    private void addAuthorizationHeader(org.springframework.ws.WebServiceMessage message) throws IOException {
        TransportContext context = TransportContextHolder.getTransportContext();
        if (context != null && context.getConnection() instanceof HttpUrlConnection connection) {
            connection.addRequestHeader("Authorization", authorizationHeader);
        }
    }
}
