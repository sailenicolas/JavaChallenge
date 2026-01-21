package com.empresa.pos.clients;

import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.pos.config.RestConfig;
import com.empresa.pos.dtos.requests.PosCostMinReq;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMin;
import com.empresa.pos.dtos.response.PosCostMinHash;
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
                .baseUrl(restConfig.getCached().getUrl())
                .build();
    }

    public Mono<ApiResponse<List<PosCostBHash>>> getPointB(String id) {
        return this.client.get()
                .uri(o -> o
                        .pathSegment("PosCost", "pointA")
                        .queryParam("idA", id)
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
                .bodyToMono(new ParameterizedTypeReference<>() {
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
                        .queryParam("idA", idPointA).queryParam("idB", idPointB).build())
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
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<ApiResponse<PosCostMin>> getMinCostBase(String source, String target) {
        return this.client.get().uri(o -> o
                        .pathSegment("PosCost", "pointMinBase")
                        .queryParam("idA", source)
                        .queryParam("idB", target)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }
}
