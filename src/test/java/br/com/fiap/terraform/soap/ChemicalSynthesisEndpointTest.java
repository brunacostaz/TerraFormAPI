package br.com.fiap.terraform.soap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.fiap.terraform.exception.InsufficientStockException;
import br.com.fiap.terraform.service.ChemicalReactionService;
import br.com.fiap.terraform.service.SynthesisService;
import br.com.fiap.terraform.soap.endpoint.ChemicalSynthesisEndpoint;
import br.com.fiap.terraform.soap.model.ProcessSynthesisRequest;
import br.com.fiap.terraform.soap.model.ProcessSynthesisResponse;
import org.junit.jupiter.api.Test;

class ChemicalSynthesisEndpointTest {

    private final ChemicalReactionService chemicalReactionService = mock(ChemicalReactionService.class);
    private final SynthesisService synthesisService = mock(SynthesisService.class);
    private final ChemicalSynthesisEndpoint endpoint = new ChemicalSynthesisEndpoint(
            chemicalReactionService,
            synthesisService
    );

    @Test
    void shouldReturnBusinessErrorWhenStockIsInsufficient() {
        ProcessSynthesisRequest request = new ProcessSynthesisRequest();
        request.setGreenhouseId(1L);
        request.setCompoundCode("NH3");
        request.setUnits(99);

        when(synthesisService.synthesize(1L, "NH3", 99))
                .thenThrow(new InsufficientStockException("Estoque insuficiente de N para sintetizar NH3."));

        ProcessSynthesisResponse response = endpoint.processSynthesis(request);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getErrorCode()).isEqualTo("ESTOQUE_INSUFICIENTE");
        assertThat(response.getMessage()).isEqualTo("Estoque insuficiente de N para sintetizar NH3.");
    }
}
