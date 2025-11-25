package com.empresa.cache.services.impl;

import com.empresa.cache.model.PosHash;
import com.empresa.cache.repositories.CachePosRepository;
import com.empresa.cache.services.CacheService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CacheServiceImpl implements CacheService<PosHash, PosHash> {
    private final CachePosRepository cachePosRepository;
    @Override
    public Optional<PosHash> getById(String id) {
        return cachePosRepository.findById(id);
    }

    @Override
    public PosHash createCache(PosHash id) {
        return cachePosRepository.save(id);
    }

    @Override
    public PosHash delete(String id) {
        cachePosRepository.deleteById(id);
        return new PosHash();
    }

    @Override
    public PosHash putCache(PosHash posHash) {
        return cachePosRepository.save(posHash);
    }

    @Override
    public List<PosHash> getAll() {
        return StreamUtils.createStreamFromIterator(cachePosRepository.findAll().iterator()).toList();
    }
}
