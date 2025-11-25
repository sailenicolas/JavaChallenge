package com.empresa.api.clients;

import com.empresa.api.config.RestConfig;
import com.empresa.api.dtos.requests.PosCostRequest;
import com.empresa.api.dtos.response.PosCostHash;
import com.empresa.api.dtos.response.PosCostMinHash;
import com.empresa.core.dtos.ApiResponse;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CachePosCostClient {
    private final WebClient client;

    public CachePosCostClient(WebClient.Builder builder, RestConfig restConfig) {
        this.client = builder
                .baseUrl(restConfig.getPos().getUrl())
                .build();
    }

    public Mono<ApiResponse<PosCostHash>> getPointB(String id, String idPointB) {
        return this.client.get()
                .uri(o -> o
                        .pathSegment("PosCost", "pointB")
                        .queryParam("id", id)
                        .queryParam("idPointB", idPointB)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PosCostHash>>() {
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
                        .queryParam("id", idPointA).queryParam("idPointB", idPointB).build())
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

    public Mono<ApiResponse<PosCostHash>> update(PosCostRequest posHash) {
        return this.client.put().uri( o -> o
                        .pathSegment("PosCost")
                        .build())
                .body(BodyInserters.fromValue(posHash))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PosCostHash>>() {
                });
    }
}
