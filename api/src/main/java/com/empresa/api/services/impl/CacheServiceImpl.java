package com.empresa.api.services.impl;

import com.empresa.api.dtos.requests.PosHashRequest;
import com.empresa.api.dtos.response.PosHash;
import com.empresa.api.services.CachePosClientService;
import com.empresa.api.services.CacheService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CacheServiceImpl implements CacheService<PosHash, PosHashRequest> {
    private final CachePosClientService cachePosRepository;
    @Override
    public Mono<PosHash> getById(String id) {
        return cachePosRepository.findById(id);
    }

    @Override
    public Mono<PosHash> createCache(PosHashRequest id) {
        return cachePosRepository.save(id);
    }

    @Override
    public Mono<PosHash> delete(String id) {
        return cachePosRepository.deleteById(id);
    }

    @Override
    public Mono<PosHash> putCache(PosHashRequest posHash) {
        return cachePosRepository.update(posHash);
    }

    @Override
    public Mono<List<PosHash>> getAll() {
        return cachePosRepository.findAll();
    }
}
