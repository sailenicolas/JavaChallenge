package com.empresa.pos.services.impl;

import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMin;
import com.empresa.pos.services.CacheClientService;
import com.empresa.pos.services.CacheExtraService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CachePosCostServiceImpl implements CacheExtraService<PosCostHash, PosCostRequest> {
    private final CacheClientService clientService;

    @Override
    public Mono<ApiResponse<PosCostHash>> getById(String id) {
        return clientService.findById(id).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosCostHash>> createCache(PosCostRequest id) {
        return clientService.save(id).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosCostHash>> delete(String id) {
        return clientService.deleteById(id).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosCostHash>> putCache(PosCostRequest posHash, String id) {
        return this.clientService.update(posHash).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<List<PosCostHash>>> getAll() {
        return clientService.findAll().map(ApiResponse::new);
    }

    @Override
    public Mono<PosCostHash> getPointB(String idPointA, String idPointB) {
        return clientService.getPointB(idPointA, idPointB);
    }

    @Override
    public Mono<PosCostMin> getPointMin(String idPointA, String idPointB) {
        return clientService.getMinCost(idPointA, idPointB);
    }
}
