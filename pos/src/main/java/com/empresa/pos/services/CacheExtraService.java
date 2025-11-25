package com.empresa.pos.services;

import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import java.util.Optional;
import reactor.core.publisher.Mono;

public interface CacheExtraService<T, V> extends CacheService<T,V> {
    Mono<PosCostHash> getPointB(String idPointA, String idPointB);

    Mono<PosCostMinHash> getPointMin(String id, String idB);
}
