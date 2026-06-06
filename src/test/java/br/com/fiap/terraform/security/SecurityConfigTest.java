package br.com.fiap.terraform.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAllowHealthWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectProtectedEndpointWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/planets"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowViewerToReadProtectedEndpoint() throws Exception {
        mockMvc.perform(get("/api/planets")
                        .with(httpBasic("viewer", "terraform-viewer")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnPortugueseMessageWhenUserHasNoPermission() throws Exception {
        mockMvc.perform(delete("/api/greenhouses/1")
                        .with(httpBasic("operator", "terraform-operator")))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message")
                        .value("Acesso negado. Seu usuario nao possui permissao para executar esta operacao."));
    }
}
