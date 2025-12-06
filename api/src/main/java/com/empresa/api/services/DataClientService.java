package com.empresa.pos.services;

import com.empresa.pos.dtos.requests.CreditsClientRequest;
import com.empresa.pos.dtos.response.CreditsResponse;
import reactor.core.publisher.Mono;

public interface DataClientService {
    CreditsResponse getById(String id);

    Mono<CreditsResponse> create(CreditsClientRequest id);
}
