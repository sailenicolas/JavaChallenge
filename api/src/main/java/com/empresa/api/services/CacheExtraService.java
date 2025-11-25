package com.empresa.api.services;

import com.empresa.api.dtos.response.PosCostHash;
import com.empresa.api.dtos.response.PosCostMinHash;
import reactor.core.publisher.Mono;

public interface CacheExtraService<T, V> extends CacheService<T,V> {
    Mono<PosCostHash> getPointB(String idPointA, String idPointB);

    Mono<PosCostMinHash> getPointMin(String id, String idB);
}
