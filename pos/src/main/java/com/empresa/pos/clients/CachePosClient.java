package com.empresa.pos.clients;

import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.pos.config.RestConfig;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.requests.PosHashRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import com.empresa.pos.dtos.response.PosHash;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CachePosClient {
    private final WebClient client;

    public CachePosClient(WebClient.Builder builder, RestConfig restConfig) {
        this.client = builder
                .baseUrl(restConfig.getCached().getUrl())
                .build();
    }
    public Mono<ApiResponse<PosHash>> save(PosHashRequest a) {
        return this.client.post().uri(o -> o
                        .pathSegment("Pos")
                        .build())
                .body(BodyInserters.fromValue(a))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PosHash>>() {
                });
    }

    public Mono<ApiResponse<List<PosHash>>> findAll() {
        return this.client.get().uri(o -> o
                        .pathSegment("Pos")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<ApiResponse<PosHash>> deleteById(String id) {
        return this.client.delete().uri(o -> o
                        .pathSegment("Pos")
                        .queryParam("id", id).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<ApiResponse<PosHash>> findById(String id) {
        return this.client.get()
                .uri(o -> o
                        .pathSegment("Pos","byId", id)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<ApiResponse<PosHash>> update(PosHashRequest posHash) {
        return this.client.put().uri( o -> o
                        .pathSegment("Pos")
                        .build())
                .body(BodyInserters.fromValue(posHash))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }
}
