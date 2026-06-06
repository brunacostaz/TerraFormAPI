package br.com.fiap.terraform.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SynthesisControllerErrorTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnConflictWhenSynthesisHasInsufficientStock() throws Exception {
        String body = """
                {
                  "compoundCode": "NH3",
                  "units": 99
                }
                """;

        mockMvc.perform(post("/api/greenhouses/1/synthesis")
                        .with(httpBasic("operator", "terraform-operator"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Estoque insuficiente de N para sintetizar NH3."));
    }
}
