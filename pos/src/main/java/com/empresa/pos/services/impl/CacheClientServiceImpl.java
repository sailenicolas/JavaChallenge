package com.empresa.pos.services.impl;

import com.empresa.pos.clients.CachePosCostClient;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import com.empresa.pos.services.CacheClientService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CacheClientServiceImpl implements CacheClientService {
    private final CachePosCostClient cachePosCostClient;
    @Override
    public Mono<PosCostHash> getPointB(String idPointA, String idPointB) {
        return cachePosCostClient.getPointB(idPointA,idPointB);
    }

    @Override
    public Mono<PosCostHash> save(PosCostRequest a) {
        return cachePosCostClient.save(a);
    }

    @Override
    public Mono<PosCostHash> deleteById(String id) {
        return this.cachePosCostClient.deleteById(id);
    }

    @Override
    public Mono<List<PosCostHash>> findAll() {
        return this.cachePosCostClient.findAll();
    }

    @Override
    public Mono<PosCostMinHash> getMinCost(String idPointA, String idPointB) {
        return this.cachePosCostClient.getMinCost(idPointA, idPointB);
    }

    @Override
    public Mono<PosCostHash> findById(String id) {
        return this.cachePosCostClient.findById(id);
    }

    @Override
    public Mono<PosCostHash> update(PosCostRequest posHash) {
        return this.cachePosCostClient.update(posHash);
    }
}
