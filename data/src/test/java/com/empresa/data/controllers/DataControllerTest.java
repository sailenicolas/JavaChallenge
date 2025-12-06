package com.empresa.data.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.data.models.CreditsModel;
import com.empresa.data.services.DataService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@WebFluxTest
@ExtendWith(SpringExtension.class)
class DataControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockitoBean
    private DataService service;

    @Test
    void getCredits() {
        CreditsModel value = new CreditsModel();
        value.setAmount("1");
        when(service.getById(any())).thenReturn(Optional.of(value));
        this.webTestClient.get()
                .uri((a)->a
                        .pathSegment("credits")
                        .queryParam("id", "1")
                        .build()
                )
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<CreditsModel>>() {})
                .consumeWith((a)->{
                    assertThat(a.getResponseBody().getData().getAmount()).isEqualTo("1");
                });
    }

    @Test
    void postCredits() {
        CreditsModel value = new CreditsModel();
        value.setAmount("1");
        when(service.createCredits(any())).thenReturn(value);
        this.webTestClient.post()
                .uri((a)->a
                        .pathSegment("credits")
                        .queryParam("id").build())
                .body(BodyInserters.fromValue(new CreditsModel()))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<CreditsModel>>() {})
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().getAmount()).isEqualTo("1");
                        }
                );
    }
}