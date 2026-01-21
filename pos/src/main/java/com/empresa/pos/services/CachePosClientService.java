package com.empresa.pos.services;

import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.pos.dtos.requests.PosHashRequest;
import com.empresa.pos.dtos.response.PosHash;
import java.util.List;
import reactor.core.publisher.Mono;

public interface CachePosClientService {
    Mono<PosHash> findById(String id);

    Mono<PosHash> save(PosHashRequest id);

    Mono<PosHash> deleteById(String id);

    Mono<List<PosHash>> findAll();

    Mono<PosHash> update(PosCostPutRequest posHash, String id);
}
