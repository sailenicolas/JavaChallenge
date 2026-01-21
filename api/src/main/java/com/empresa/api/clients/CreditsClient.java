package com.empresa.api.clients;

import com.empresa.api.config.RestConfig;
import com.empresa.api.dtos.requests.CreditsClientRequest;
import com.empresa.api.dtos.response.CreditsResponse;
import com.empresa.core.dtos.responses.ApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CreditsClient {
    private final WebClient client;

    public CreditsClient(WebClient.Builder builder, RestConfig restConfig) {
        this.client = builder
                .baseUrl(restConfig.getData().getUrl())
                .build();
    }
    public Mono<ApiResponse<CreditsResponse>> create(CreditsClientRequest id) {
        return this.client.post().uri(o -> o
                        .pathSegment("credits")
                        .build())
                .body(BodyInserters.fromValue(id))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<ApiResponse<CreditsResponse>> getById(String id) {
        return this.client.get().uri(o -> o
                        .pathSegment("credits")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }
}
