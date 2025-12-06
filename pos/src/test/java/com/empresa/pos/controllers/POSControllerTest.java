package com.empresa.pos.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.pos.dtos.requests.PosHashRequest;
import com.empresa.pos.dtos.response.PosHash;
import com.empresa.pos.services.impl.CacheServiceImpl;
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

@WebFluxTest(controllers = CachePOSController.class)
@ExtendWith(SpringExtension.class)
class POSControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockitoBean
    private CacheServiceImpl service;

    @BeforeEach
    void setUp() {
    }

    /**
     * Class under test: {@link CachePOSController#get(String)}
     */
    @Test
    void get() {
        when(service.getById(any())).thenReturn(Mono.justOrEmpty(new PosHash("1", "1")).map(ApiResponse::new));
        this.webTestClient.get()
                .uri((a)->a
                        .pathSegment("Pos", "byId", "{id}")
                        .queryParam("id")
                        .build("1")
                )
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<PosHash>>() {})
                .consumeWith((a)->{
                assertThat(a.getResponseBody().getData().getPoint()).isEqualTo("1");
        });
    }

    /**
     * Class under test: {@link CachePOSController#getAll()}
     */
    @Test
    void getAll() {
        when(this.service.getAll()).thenReturn(Mono.justOrEmpty(Collections.singletonList(new PosHash("1", "1"))).map(ApiResponse::new));
        this.webTestClient.get().uri((a)->a
                        .pathSegment("Pos")
                        .queryParam("id").build())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<List<PosHash>>>() {
                })
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().getFirst().getPoint()).isEqualTo("1");
                        }
                );
    }

    /**
     * Class under test: {@link CachePOSController#create(PosHashRequest)}
     */
    @Test
    void create() {
        when(service.createCache(any())).thenReturn(Mono.justOrEmpty(new PosHash("1", "1")).map(ApiResponse::new));
        this.webTestClient.post()
                .uri((a)->a
                        .pathSegment("Pos")
                        .queryParam("id").build())
                .body(BodyInserters.fromValue(new PosHashRequest()))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<PosHash>>() {})
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().getPoint()).isEqualTo("1");
                        }
                );
    }

    /**
     * Class under test: {@link CachePOSController#put(PosHashRequest, String)}
     */
    @Test
    void put() {
        when(service.putCache(any(), any())).thenReturn(Mono.justOrEmpty(new PosHash("1", "1")).map(ApiResponse::new));
        this.webTestClient.put().uri((a)->a
                        .pathSegment("Pos")
                        .queryParam("id", "1").build())
                .body(BodyInserters.fromValue(new PosHashRequest()))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<ApiResponse<PosHash>>() {})
                .consumeWith((a)->{
                            assertThat(a.getResponseBody().getData().getPoint()).isEqualTo("1");
                        }
                );
    }

    /**
     * Class under test: {@link CachePOSController#delete(String)}
     */
    @Test
    void delete() {
        when(this.service.delete(any())).thenReturn(Mono.justOrEmpty(new PosHash()).map(ApiResponse::new));
        this.webTestClient.delete()
                .uri((a)->a
                        .pathSegment("Pos")
                        .queryParam("id", "1").build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectAll(o ->{
                    o.expectHeader().contentType(MediaType.APPLICATION_JSON);
                })
                .expectBody(new ParameterizedTypeReference<PosHash>() {
                })
                .consumeWith((a)->{
                    PosHash responseBody = a.getResponseBody();
                    assertThat(responseBody).isNotNull();
                    assertThat(responseBody.getPoint()).isNull();
                        }
                );
    }

}