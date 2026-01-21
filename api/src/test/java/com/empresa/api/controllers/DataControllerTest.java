package com.empresa.api.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.empresa.api.dtos.requests.CreditsRequest;
import com.empresa.api.dtos.response.CreditsResponse;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.api.services.DataService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

@WebFluxTest(CreditsController.class)
@ExtendWith(SpringExtension.class)
class DataControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockitoBean
    private DataService service;

    @Test
    void getCredits() {
        CreditsResponse value = new CreditsResponse();
        value.setAmount("1");
        when(service.getById(any())).thenReturn(Mono.just(value));
        this.webTestClient.get()
                .uri((a)->a
                        .pathSegment("Credits")
                        .queryParam("id", "1")
                        .build()
                )
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<CreditsResponse>>() {})
                .consumeWith((a)->{
                    assertThat(a.getResponseBody().getData().getAmount()).isEqualTo("1");
                });
    }

    @Test
    void postCredits() {
        CreditsResponse value = new CreditsResponse();
        value.setAmount("1");
        when(service.createCredits(any())).thenReturn(Mono.just(value));
        this.webTestClient.post()
                .uri((a)->a
                        .pathSegment("Credits")
                        .queryParam("id").build())
                .body(BodyInserters.fromValue(new CreditsRequest("1", new BigDecimal("1"))))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<CreditsResponse>>() {})
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().getAmount()).isEqualTo("1");
                        }
                );
    }
}