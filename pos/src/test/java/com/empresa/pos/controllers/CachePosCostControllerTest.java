package com.empresa.pos.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import com.empresa.pos.services.CacheExtraService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CachePosCostController.class)

class CachePosCostControllerTest {
    public static final String POS = "PosCost";
    @Autowired
    private WebTestClient webTestClient;
    @MockitoBean
    private CacheExtraService<PosCostHash, PosCostRequest> service;

    @BeforeEach
    void setUp() {
    }
    /**
     * Class under test: {@link CachePosCostController#getPointC(String, String)}
     */
    @Test
    void getPointC() {
        when(this.service.getById(any())).thenReturn(Mono.just(new PosCostHash("1", "1", "1", new BigDecimal("1"))).map(ApiResponse::new));
        this.webTestClient.get().uri((a)->a
                        .pathSegment(POS, "byIds")
                        .queryParam("idA", "1")
                        .queryParam("idB", "2").build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<PosCostHash>>() {
                })
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().getId()).isEqualTo("1");
                        }
                );
    }

    @Test
    void getPointMin() {
        PosCostBHash posCostHash = new PosCostBHash("", "", "", "", "", new BigDecimal("1"));
        List<PosCostBHash> points = List.of(posCostHash);
        when(this.service.getPointMin(any(), anyString())).thenReturn(Mono.just(new PosCostMinHash ("1",  points, new BigDecimal("1"))).map(ApiResponse::new));
        this.webTestClient.get().uri((a)->a
                        .pathSegment(POS, "pointMin")
                        .queryParam("idA", "1")
                        .queryParam("idB", "2").build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<PosCostMinHash>>() {
                })
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().getPoints()).isNotEmpty();
                        }
                );
    }
}