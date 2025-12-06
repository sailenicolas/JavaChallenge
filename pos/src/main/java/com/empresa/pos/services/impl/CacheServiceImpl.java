package com.empresa.pos.services.impl;

import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.services.CrudService;
import com.empresa.pos.dtos.requests.PosHashRequest;
import com.empresa.pos.dtos.response.PosHash;
import com.empresa.pos.services.CachePosClientService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CacheServiceImpl implements CrudService<PosHash, PosHashRequest> {
    private final CachePosClientService cachePosRepository;
    @Override
    public Mono<ApiResponse<PosHash>> getById(String id) {
        return cachePosRepository.findById(id).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosHash>> createCache(PosHashRequest id) {
        return cachePosRepository.save(id).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosHash>> delete(String id) {
        return cachePosRepository.deleteById(id).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosHash>> putCache(PosHashRequest posHash, String id) {
        return cachePosRepository.update(posHash).map(ApiResponse::new);
    }
    @Override
    public Mono<ApiResponse<List<PosHash>>> getAll() {
        return cachePosRepository.findAll().map(ApiResponse::new);
    }
}
