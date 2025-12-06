package com.empresa.pos.services;

import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMin;
import com.empresa.pos.dtos.response.PosCostMinHash;
import java.util.List;
import reactor.core.publisher.Mono;

public interface CacheClientService {
    Mono<PosCostHash> getPointB(String idPointA, String idPointB);

    Mono<PosCostHash> save(PosCostRequest a);

    Mono<PosCostHash> deleteById(String id);

    Mono<List<PosCostHash>> findAll();

    Mono<PosCostMin> getMinCost(String idPointA, String idPointB);

    Mono<PosCostHash> findById(String id);

    Mono<PosCostHash> update(PosCostRequest posHash);

    Mono<PosCostMinHash> findMinBase(String source, String target);
}
