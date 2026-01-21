package com.empresa.cache.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.empresa.cache.dtos.requests.PosCostRequest;
import com.empresa.cache.dtos.requests.PosHashRequest;
import com.empresa.cache.dtos.requests.PostCostMinRequest;
import com.empresa.cache.dtos.response.PosCostMinBase;
import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.model.PosCostMinHash;
import com.empresa.cache.services.CrudExtraService;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PosCostController.class)
class PosCostControllerTest {

    public static final String POS = "PosCost";
    @Autowired
    private WebTestClient webTestClient;
    @MockitoBean
    private CrudExtraService<PosCostHash, PosCostRequest> service;

    @BeforeEach
    void setUp() {
    }

    /**
     * Class under test: {@link PosCostController#get(String)}
     */
    @Test
    void get() {
        when(service.getById(any())).thenReturn(Mono.justOrEmpty(new PosCostHash("1", "1", "1", new BigDecimal("1"))).map(ApiResponse::new));
        this.webTestClient.get()
                .uri((a)->a
                        .pathSegment(POS, "byId", "{id}")
                        .queryParam("id")
                        .build("1")
                )
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<PosCostHash>>() {})
                .consumeWith((a)->{
                    assertThat(a.getResponseBody().getData().getId()).isEqualTo("1");
                });
    }

    /**
     * Class under test: {@link PosCostController#getAll()}
     */
    @Test
    void getAll() {
        when(this.service.getAll()).thenReturn(Mono.justOrEmpty(Collections.singletonList(new PosCostHash("1", "1", "1", new BigDecimal("1")))).map(ApiResponse::new));
        this.webTestClient.get().uri((a)->a
                        .pathSegment(POS)
                        .queryParam("id").build())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<List<PosCostHash>>>() {
                })
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().getFirst().getId()).isEqualTo("1");
                        }
                );
    }

    /**
     * Class under test: {@link PosCostController#create(PosCostRequest)}
     */
    @Test
    void create() {
        when(service.createCache(any())).thenReturn(Mono.justOrEmpty(new PosCostHash("1", "1", "1", new BigDecimal("1"))).map(ApiResponse::new));
        this.webTestClient.post()
                .uri((a)->a
                        .pathSegment(POS)
                        .queryParam("id").build())
                .body(BodyInserters.fromValue(new PosHashRequest()))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<PosCostHash>>() {})
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().getId()).isEqualTo("1");
                        }
                );
    }

    /**
     * Class under test: {@link PosCostController#put(PosCostPutRequest, String)}
     */
    @Test
    void put() {
        when(service.putCache(any(), any())).thenReturn(Mono.justOrEmpty(new PosCostHash("1", "1", "1", new BigDecimal("1"))).map(ApiResponse::new));
        this.webTestClient.put().uri((a)->a
                        .pathSegment(POS)
                        .queryParam("id", "1").build())
                .body(BodyInserters.fromValue(new PosCostPutRequest()))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<PosCostHash>>() {})
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().getId()).isEqualTo("1");
                        }
                );
    }

    /**
     * Class under test: {@link PosCostController#delete(String)}
     */
    @Test
    void delete() {
        when(this.service.delete(any())).thenReturn(Mono.justOrEmpty(new PosCostHash()).map(ApiResponse::new));
        this.webTestClient.delete()
                .uri((a)->a
                        .pathSegment(POS)
                        .queryParam("id", "1").build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectAll(o ->{
                    o.expectHeader().contentType(MediaType.APPLICATION_JSON);
                })
                .expectBody(new ParameterizedTypeReference<PosCostHash>() {
                })
                .consumeWith((a)->{
                            PosCostHash responseBody = a.getResponseBody();
                            assertThat(responseBody).isNotNull();
                            assertThat(responseBody.getId()).isNull();
                        }
                );
    }
    /**
     * Class under test: {@link PosCostController#getPointB(String)}
     */
    @Test
    void getPointB() {
        ApiResponse<List<PosCostBHash>> tApiResponse = new ApiResponse<>(List.of(new PosCostBHash("1","1", "1", "1", "1", new BigDecimal("1"))));
        when(this.service.getPointA(any())).thenReturn(Optional.of(tApiResponse));
        this.webTestClient.get().uri((a)->a
                        .pathSegment(POS, "pointA")
                        .queryParam("idA", "1")
                        .build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<List<PosCostBHash>>>() {
                })
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().getFirst().getId()).isEqualTo("1");
                        }
                );
    }



    /**
     * Class under test: {@link PosCostController#getPointMin(String, String)}
     */
    @Test
    void getPointMinBase() {
        when(this.service.getPointMinBase(any(), anyString())).thenReturn(new ApiResponse<>(new PosCostMinBase(Collections.singletonList(new PosCostBHash("1", "1","1","1", "1", new BigDecimal("1"))), Collections.singletonList(new PosCostBHash()))));
        Optional<ApiResponse<PosCostMinHash>> t = Optional.of(new PosCostMinHash(new PostCostMinRequest(Collections.singletonList(new PosCostBHash("1", "1","1","1", "1", new BigDecimal("1"))), new BigDecimal("1"), "1", "1"), "1")).map(ApiResponse::new);
        this.webTestClient.get().uri((a)->a
                        .pathSegment(POS, "pointMinBase")
                        .queryParam("idA", "1")
                        .queryParam("idB", "2").build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<PosCostMinBase>>() {
                })
                .consumeWith((a)->{
                    assertThat(a.getResponseBody().getData().outgoing().getFirst().getId()).isEqualTo("1");
                        }
                );
    }
}