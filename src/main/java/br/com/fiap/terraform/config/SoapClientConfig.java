package br.com.fiap.terraform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class SoapClientConfig {

    @Bean
    public WebServiceTemplate chemicalSynthesisWebServiceTemplate(
            Jaxb2Marshaller marshaller,
            @Value("${terraform.soap.chemical-synthesis-url:http://localhost:8080/ws}") String soapUrl) {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
        template.setDefaultUri(soapUrl);
        return template;
    }
}

