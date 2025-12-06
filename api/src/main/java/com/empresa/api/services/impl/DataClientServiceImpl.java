package com.empresa.pos.services.impl;

import com.empresa.pos.clients.CreditsClient;
import com.empresa.pos.dtos.requests.CreditsClientRequest;
import com.empresa.pos.dtos.response.CreditsResponse;
import com.empresa.pos.services.DataClientService;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.exceptions.NotFoundServiceException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DataClientServiceImpl implements DataClientService {
    private final CreditsClient creditsClient;

    @Override
    @Cacheable(value = "data", key = "#id", unless = "#result == null")
    public CreditsResponse getById(String id) {
        return creditsClient.getById(id)
                .map(ApiResponse::getData)
                .blockOptional()
                .orElseThrow(NotFoundServiceException::new);
    }

    @Override
    public Mono<CreditsResponse> create(CreditsClientRequest id) {
        return this.creditsClient.create(id)
                .map(ApiResponse::getData);
    }
}
