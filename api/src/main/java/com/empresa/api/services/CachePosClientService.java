package com.empresa.api.services;

import com.empresa.api.dtos.requests.PosHashRequest;
import com.empresa.api.dtos.response.PosHash;
import java.util.List;
import reactor.core.publisher.Mono;

public interface CachePosClientService {
    Mono<PosHash> findById(String id);

    Mono<PosHash> save(PosHashRequest id);

    Mono<PosHash> deleteById(String id);

    Mono<List<PosHash>> findAll();

    Mono<PosHash> update(PosHashRequest posHash);
}
