package com.empresa.pos.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.requests.PosHashRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.services.CacheExtraService;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
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
@WebFluxTest(controllers = CachePosCostController.class)
class PosCostControllerTest {

    public static final String POS = "PosCost";
    @Autowired
    private WebTestClient webTestClient;
    @MockitoBean
    private CacheExtraService<PosCostHash, PosCostRequest> service;

    @BeforeEach
    void setUp() {
    }

    /**
     * Class under test: {@link CachePosCostController#get(String)}
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
     * Class under test: {@link CachePosCostController#getAll()}
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
     * Class under test: {@link CachePosCostController#create(PosCostRequest)}
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
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<PosCostHash>>() {})
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().getId()).isEqualTo("1");
                        }
                );
    }

    /**
     * Class under test: {@link CachePosCostController#put(PosCostPutRequest, String)}
     */
    @Test
    void put() {
        when(service.putCache(any(), any())).thenReturn(Mono.justOrEmpty(new PosCostHash("1", "1", "1", new BigDecimal("1"))).map(ApiResponse::new));
        this.webTestClient.put().uri((a)->a
                        .pathSegment(POS)
                        .queryParam("id", "1").build())
                .body(BodyInserters.fromValue(new PosHashRequest()))
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
     * Class under test: {@link CachePosCostController#delete(String)}
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
                .expectBody(new ParameterizedTypeReference<ApiResponse<PosCostHash>>() {
                })
                .consumeWith((a)->{
                            PosCostHash responseBody = a.getResponseBody().getData();
                            assertThat(responseBody).isNotNull();
                            assertThat(responseBody.getId()).isNull();
                        }
                );
    }
    /**
     * Class under test: {@link CachePosCostController#getPointB(String)}
     */
    @Test
    void getPointB() {
        when(this.service.getPointB(any())).thenReturn(Mono.just(List.of(new PosCostBHash("1","1", "1", "1", "1", new BigDecimal("1")))).map(ApiResponse::new));
        this.webTestClient.get().uri((a)->a
                        .pathSegment(POS, "pointA")
                        .queryParam("idPointA", "1")
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
}