package com.empresa.pos.services;

import com.empresa.core.services.CrudService;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMin;
import reactor.core.publisher.Mono;

public interface CacheExtraService<T, V> extends CrudService<T,V> {
    Mono<PosCostHash> getPointB(String idPointA, String idPointB);

    Mono<PosCostMin> getPointMin(String id, String idB);
}
