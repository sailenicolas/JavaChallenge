package com.empresa.pos.services;

import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMin;
import java.util.List;
import reactor.core.publisher.Mono;

public interface CacheClientService {
    Mono<PosCostHash> findById(String id);

    Mono<PosCostHash> update(PosCostPutRequest posHash, String id);
    Mono<PosCostHash> save(PosCostRequest a);

    Mono<PosCostHash> deleteById(String id);
    Mono<List<PosCostBHash>> getPointB(String idPointA);


    Mono<List<PosCostHash>> findAll();

    Mono<PosCostMin> getMinCostBase(String idPointA, String idPointB);

}
