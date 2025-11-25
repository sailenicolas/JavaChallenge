package com.empresa.api.services.impl;

import com.empresa.api.clients.CachePosCostClient;
import com.empresa.api.dtos.requests.PosCostRequest;
import com.empresa.api.dtos.response.PosCostHash;
import com.empresa.api.dtos.response.PosCostMinHash;
import com.empresa.api.services.CacheClientService;
import com.empresa.core.dtos.ApiResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CacheClientServiceImpl implements CacheClientService {
    private final CachePosCostClient cachePosCostClient;
    @Override
    public Mono<PosCostHash> getPointB(String idPointA, String idPointB) {
        return cachePosCostClient.getPointB(idPointA,idPointB)
                .map(ApiResponse::getData);
    }

    @Override
    public Mono<PosCostHash> save(PosCostRequest a) {
        return cachePosCostClient.save(a)
                .map(ApiResponse::getData);
    }

    @Override
    public Mono<PosCostHash> deleteById(String id) {
        return this.cachePosCostClient.deleteById(id)
                .map(ApiResponse::getData);
    }

    @Override
    public Mono<List<PosCostHash>> findAll() {
        return this.cachePosCostClient.findAll()
                .map(ApiResponse::getData);
    }

    @Override
    public Mono<PosCostMinHash> getMinCost(String idPointA, String idPointB) {
        return this.cachePosCostClient.getMinCost(idPointA, idPointB)
                .map(ApiResponse::getData);
    }

    @Override
    public Mono<PosCostHash> findById(String id) {
        return this.cachePosCostClient.findById(id)
                .map(ApiResponse::getData);
    }

    @Override
    public Mono<PosCostHash> update(PosCostRequest posHash) {
        return this.cachePosCostClient.update(posHash)
                .map(ApiResponse::getData);
    }
}
