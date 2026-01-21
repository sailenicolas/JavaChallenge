package com.empresa.api.services;

import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.api.dtos.requests.PosCostRequest;
import com.empresa.api.dtos.response.PosCostHash;
import com.empresa.api.dtos.response.PosCostMinHash;
import com.empresa.core.dtos.responses.PosCostBHash;
import java.util.List;
import reactor.core.publisher.Mono;

public interface CacheClientService {
    List<PosCostBHash> getPointB(String idPointA);

    Mono<PosCostHash> save(PosCostRequest a);

    Mono<PosCostHash> deleteById(String id);

    List<PosCostHash> findAll();

    PosCostMinHash getMinCost(String idPointA, String idPointB);

    PosCostHash findById(String id);

    Mono<PosCostHash> update(PosCostPutRequest posHash, String id);
}
