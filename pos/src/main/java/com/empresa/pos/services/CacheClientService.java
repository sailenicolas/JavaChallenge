package com.empresa.pos.services;

import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import java.util.List;
import java.util.Optional;
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
