package com.empresa.pos.services.impl;

import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.pos.clients.CachePosCostClient;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMin;
import com.empresa.pos.services.CacheClientService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CacheClientServiceImpl implements CacheClientService {
    private final CachePosCostClient cachePosCostClient;
    @Override
    public Mono<List<PosCostBHash>> getPointB(String idPointA) {
        return cachePosCostClient.getPointB(idPointA)
                .map(ApiResponse::getData);
    }

    @Override
    public Mono<PosCostHash> save(PosCostRequest a) {
        return cachePosCostClient.save(a)
                .map(ApiResponse::getData);
    }

    @Override
    public Mono<PosCostHash> deleteById(String id) {
        return this.cachePosCostClient.deleteById(id).map(ApiResponse::getData);
    }

    @Override
    public Mono<List<PosCostHash>> findAll() {
        return this.cachePosCostClient.findAll()
                .map(ApiResponse::getData);
    }

    @Override
    public Mono<PosCostMin> getMinCostBase(String idPointA, String idPointB) {
        return this.cachePosCostClient.getMinCostBase(idPointA, idPointB)
                .map(ApiResponse::getData);
    }

    @Override
    public Mono<PosCostHash> findById(String id) {
        return this.cachePosCostClient.findById(id)
                .map(ApiResponse::getData);
    }

    @Override
    public Mono<PosCostHash> update(PosCostPutRequest posHash, String id) {
        return this.cachePosCostClient.update(posHash, id)
                .map(ApiResponse::getData);
    }
}
