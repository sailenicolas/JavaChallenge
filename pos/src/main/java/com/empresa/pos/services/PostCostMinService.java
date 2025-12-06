package com.empresa.pos.services;

import com.empresa.pos.dtos.response.PosCostMin;
import com.empresa.pos.dtos.response.PosCostMinHash;
import reactor.core.publisher.Mono;

public interface PostCostMinService {
    Mono<PosCostMinHash> findById(String source, String target);

    PosCostMinHash shortestPath(String source, String target, PosCostMin o);
}