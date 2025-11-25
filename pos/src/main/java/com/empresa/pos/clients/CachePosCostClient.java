package com.empresa.pos.clients;

import com.empresa.pos.config.RestConfig;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
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

    public Mono<PosCostHash> getPointB(String id, String idPointB) {
        return this.client.get()
                .uri(o -> o
                        .pathSegment("PosCost", "pointB")
                        .queryParam("id", id)
                        .queryParam("idPointB", idPointB)
                        .build())
                .retrieve()
                .bodyToMono(PosCostHash.class);
    }

    public Mono<PosCostHash> save(PosCostRequest a) {
        return this.client.post().uri(o -> o
                        .pathSegment("PosCost")
                        .build())
                .body(BodyInserters.fromValue(a))
                .retrieve()
                .bodyToMono(PosCostHash.class);
    }

    public Mono<List<PosCostHash>> findAll() {
        return this.client.get().uri(o -> o
                        .pathSegment("PosCost")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<PosCostMinHash> getMinCost(String idPointA, String idPointB) {
        return this.client.get().uri(o -> o
                        .pathSegment("PosCost", "pointMin")
                        .queryParam("id", idPointA).queryParam("idPointB", idPointB).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<PosCostHash> deleteById(String id) {
        return this.client.delete().uri(o -> o
                        .pathSegment("PosCost")
                        .queryParam("id", id).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<PosCostHash> findById(String id) {
        return this.client.get()
                .uri(o -> o
                        .pathSegment("PosCost","byId", "{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<PosCostHash> update(PosCostRequest posHash) {
        return this.client.put().uri( o -> o
                        .pathSegment("PosCost")
                        .build())
                .body(BodyInserters.fromValue(posHash))
                .retrieve()
                .bodyToMono(PosCostHash.class);
    }
}
