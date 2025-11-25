package com.empresa.api.services.impl;

import com.empresa.api.clients.DataClient;
import com.empresa.api.dtos.requests.CreditsRequest;
import com.empresa.api.dtos.requests.CreditsClientRequest;
import com.empresa.api.dtos.response.CreditsResponse;
import com.empresa.api.services.DataClientService;
import com.empresa.core.dtos.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DataClientServiceImpl implements DataClientService {
    private final DataClient dataClient;

    @Override
    public Mono<CreditsResponse> getById(String id) {
        return dataClient.getById(id).map(ApiResponse::getData);
    }

    @Override
    public Mono<CreditsResponse> create(CreditsClientRequest id) {
        return this.dataClient.create(id).map(ApiResponse::getData);
    }
}
