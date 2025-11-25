package com.empresa.api.services.impl;

import com.empresa.api.clients.CachePosClient;
import com.empresa.api.dtos.requests.PosHashRequest;
import com.empresa.api.dtos.response.PosHash;
import com.empresa.api.services.CachePosClientService;
import com.empresa.core.dtos.ApiResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CachePosClientServiceImpl implements CachePosClientService {
    private final CachePosClient cachePosClient;
    @Override
    public Mono<PosHash> findById(String id) {
        return cachePosClient.findById(id).map(ApiResponse::getData);
    }

    @Override
    public Mono<PosHash> save(PosHashRequest id) {
        return cachePosClient.save(id).map(ApiResponse::getData);
    }

    @Override
    public Mono<PosHash> deleteById(String id) {
        return this.cachePosClient.deleteById(id).map(ApiResponse::getData);

    }

    @Override
    public Mono<List<PosHash>> findAll() {
        return this.cachePosClient.findAll().map(ApiResponse::getData);
    }

    @Override
    public Mono<PosHash> update(PosHashRequest posHash) {
        return this.cachePosClient.update(posHash).map(ApiResponse::getData);
    }
}
