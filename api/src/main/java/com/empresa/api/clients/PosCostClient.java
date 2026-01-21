package com.empresa.api.clients;

import com.empresa.api.config.RestConfig;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.api.dtos.requests.PosCostRequest;
import com.empresa.api.dtos.response.PosCostHash;
import com.empresa.api.dtos.response.PosCostMinHash;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PosCostClient {
    private final WebClient client;

    public PosCostClient(WebClient.Builder builder, RestConfig restConfig) {
        this.client = builder
                .baseUrl(restConfig.getPos().getUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<ApiResponse<List<PosCostBHash>>> getPointB(String id) {
        return this.client.get()
                .uri(o -> o
                        .pathSegment("PosCost", "pointA")
                        .queryParam("idPointA", id)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<ApiResponse<PosCostHash>> save(PosCostRequest a) {
        return this.client.post().uri(o -> o
                        .pathSegment("PosCost")
                        .build())
                .body(BodyInserters.fromValue(a))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PosCostHash>>() {
                });
    }

    public Mono<ApiResponse<List<PosCostHash>>> findAll() {
        return this.client.get().uri(o -> o
                        .pathSegment("PosCost")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<ApiResponse<PosCostMinHash>> getMinCost(String idPointA, String idPointB) {
        return this.client.get().uri(o -> o
                        .pathSegment("PosCost", "pointMin")
                        .queryParam("idA", idPointA)
                        .queryParam("idB", idPointB).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<ApiResponse<PosCostHash>> deleteById(String id) {
        return this.client.delete().uri(o -> o
                        .pathSegment("PosCost")
                        .queryParam("id", id).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<ApiResponse<PosCostHash>> findById(String id) {
        return this.client.get()
                .uri(o -> o
                        .pathSegment("PosCost","byId", "{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<ApiResponse<PosCostHash>> update(PosCostPutRequest posHash, String id) {
        return this.client.put().uri( o -> o
                        .pathSegment("PosCost")
                        .queryParam("id", id)
                        .build())
                .body(BodyInserters.fromValue(posHash))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PosCostHash>>() {
                });
    }
}
