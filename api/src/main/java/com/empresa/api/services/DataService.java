package com.empresa.pos.services;

import com.empresa.pos.dtos.requests.CreditsRequest;
import com.empresa.pos.dtos.response.CreditsResponse;
import reactor.core.publisher.Mono;

public interface DataService {
    Mono<CreditsResponse> getById(String id);

    Mono<CreditsResponse> createCredits(CreditsRequest id);
}
