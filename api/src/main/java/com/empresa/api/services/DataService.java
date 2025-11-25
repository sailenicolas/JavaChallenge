package com.empresa.api.services;

import com.empresa.api.dtos.requests.CreditsRequest;
import com.empresa.api.dtos.response.CreditsResponse;
import reactor.core.publisher.Mono;

public interface DataService {
    Mono<CreditsResponse> getById(String id);

    Mono<CreditsResponse> createCredits(CreditsRequest id);
}
