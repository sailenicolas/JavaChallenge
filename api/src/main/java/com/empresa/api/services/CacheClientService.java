package com.empresa.api.services;

import com.empresa.api.dtos.requests.PosCostRequest;
import com.empresa.api.dtos.response.PosCostHash;
import com.empresa.api.dtos.response.PosCostMinHash;
import java.util.List;
import reactor.core.publisher.Mono;

public interface CacheClientService {
    Mono<PosCostHash> getPointB(String idPointA, String idPointB);

    Mono<PosCostHash> save(PosCostRequest a);

    Mono<PosCostHash> deleteById(String id);

    Mono<List<PosCostHash>> findAll();

    Mono<PosCostMinHash> getMinCost(String idPointA, String idPointB);

    Mono<PosCostHash> findById(String id);

    Mono<PosCostHash> update(PosCostRequest posHash);
}
