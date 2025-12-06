package com.empresa.pos.services;

import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import java.util.List;
import reactor.core.publisher.Mono;

public interface CacheClientService {
    PosCostHash getPointB(String idPointA, String idPointB);

    Mono<PosCostHash> save(PosCostRequest a);

    Mono<PosCostHash> deleteById(String id);

    List<PosCostHash> findAll();

    PosCostMinHash getMinCost(String idPointA, String idPointB);

    PosCostHash findById(String id);

    Mono<PosCostHash> update(PosCostRequest posHash);
}
