package com.empresa.data.services;

import com.empresa.data.models.CreditsModel;
import reactor.core.publisher.Mono;

public interface DataService {
    Mono<CreditsModel> getById(String id);

    Mono<CreditsModel> createCredits(CreditsModel id);
}
