package com.empresa.pos.services;

import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import com.empresa.core.services.CrudService;
import reactor.core.publisher.Mono;

public interface CrudExtraService<T, V> extends CrudService<T,V> {
    Mono<PosCostHash> getPointB(String idPointA, String idPointB);

    Mono<PosCostMinHash> getPointMin(String id, String idB);
}
