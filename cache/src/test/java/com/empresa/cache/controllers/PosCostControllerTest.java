package com.empresa.cache.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.empresa.cache.dtos.requests.PosCostRequest;
import com.empresa.cache.dtos.requests.PosHashRequest;
import com.empresa.cache.dtos.requests.PostCostMinRequest;
import com.empresa.cache.dtos.response.PosCostMin;
import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.model.PosCostMinHash;
import com.empresa.cache.model.PosHash;
import com.empresa.cache.services.CrudExtraService;
import com.empresa.cache.services.impl.CrudServiceImpl;
import com.empresa.core.dtos.responses.ApiResponse;
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
     * Class under test: {@link PosCostController#put(PosCostRequest, String)}
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
     * Class under test: {@link PosCostController#getPointB(String, String)}
     */
    @Test
    void getPointB() {
        when(this.service.getPointB(any(), anyString())).thenReturn(Optional.of(new PosCostHash("1", "1", "1", new BigDecimal("1"))).map(ApiResponse::new));
        this.webTestClient.get().uri((a)->a
                        .pathSegment(POS, "pointB")
                        .queryParam("id", "1")
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

    /**
     * Class under test: {@link PosCostController#getPointsMin(String, String)}
     */
    @Test
    void getPointsMin() {
        when(this.service.getPointsMin(any(), anyString())).thenReturn(new ApiResponse<>(new PosCostMin(Collections.singletonList(new PosCostHash("1", "1", "1", new BigDecimal("1"))), Collections.singletonList(new PosCostHash()))));
        this.webTestClient.get().uri((a)->a
                        .pathSegment(POS, "pointMin")
                        .queryParam("id", "1")
                        .queryParam("idB", "2").build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<PosCostMin>>() {
                })
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().outgoing().getFirst().getId()).isEqualTo("1");
                        }
                );
    }

    /**
     * Class under test: {@link PosCostController#postPointMin(PostCostMinRequest)}
     */
    @Test
    void postPointMin() {
        when(this.service.postCostMin(any())).thenReturn(new ApiResponse<>(new PosCostMinHash(new PostCostMinRequest(Collections.singletonList(new PosCostHash("1", "1", "1", new BigDecimal("1"))), new BigDecimal("1"), "1", "1"), "1")));
        this.webTestClient.post().uri((a)->a
                        .pathSegment(POS, "pointMin")
                        .queryParam("id", "1")
                        .queryParam("idB", "2").build())
                .body(BodyInserters.fromValue(new PostCostMinRequest(new ArrayList<>(Collections.singleton(new PosCostHash())), new BigDecimal("1"), "", "")))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<PosCostMinHash>>() {
                })
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().getPoints().getFirst().getId()).isEqualTo("1");
                        }
                );
    }

    /**
     * Class under test: {@link PosCostController#postPointBase(String, String)}
     */
    @Test
    void postPointBase() {
        Optional<ApiResponse<PosCostMinHash>> t = Optional.of(new PosCostMinHash(new PostCostMinRequest(Collections.singletonList(new PosCostHash("1", "1", "1", new BigDecimal("1"))), new BigDecimal("1"), "1", "1"), "1")).map(ApiResponse::new);
        when(this.service.getPointMinBase(any(), anyString())).thenReturn(t);
        this.webTestClient.get().uri((a)->a
                        .pathSegment(POS, "pointMinBase")
                        .queryParam("idA", "1")
                        .queryParam("idB", "2").build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<PosCostMinHash>>() {
                })
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().getPoints().getFirst().getId()).isEqualTo("1");
                        }
                );
    }
}