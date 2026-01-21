package com.empresa.api.services;

import com.empresa.api.dtos.requests.CreditsClientRequest;
import com.empresa.api.dtos.response.CreditsResponse;
import reactor.core.publisher.Mono;

public interface DataClientService {
    CreditsResponse getById(String id);

    Mono<CreditsResponse> create(CreditsClientRequest id);
}
