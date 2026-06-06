package br.com.fiap.terraform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI terraFormOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("TerraForm API")
                        .version("1.0.0")
                        .description("""
                                API REST e SOAP para gerenciamento de estufas espaciais hermeticas.
                                A documentacao REST descreve dashboard, estoque, sintese quimica,
                                aplicacao de compostos, logs e simulacao de telemetria.
                                """)
                        .contact(new Contact()
                                .name("TerraForm - FIAP Global Solution")
                                .url("https://github.com/brunacostaz/TerraFormAPI"))
                        .license(new License()
                                .name("Projeto academico")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Ambiente local com Docker ou IntelliJ")
                ))
                .components(new Components()
                        .addSecuritySchemes("basicAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"));
    }
}
