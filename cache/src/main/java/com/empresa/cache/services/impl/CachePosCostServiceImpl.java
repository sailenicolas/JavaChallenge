package com.empresa.cache.services.impl;

import com.empresa.cache.dtos.requests.PosCostRequest;
import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.model.PosCostMinHash;
import com.empresa.cache.repositories.CachePosCostRepository;
import com.empresa.cache.services.CacheExtraService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CachePosCostServiceImpl implements CacheExtraService<PosCostHash, PosCostRequest> {
    private final CachePosCostRepository cachePosCostRepository;
    private final CostService costService;

    @Override
    public Optional<PosCostHash> getById(String id) {
        return cachePosCostRepository.findById(id);
    }

    @Override
    public PosCostHash createCache(PosCostRequest id) {
        var a = getPosCostHash(id);
        return cachePosCostRepository.save(a);
    }

    private static PosCostHash getPosCostHash(PosCostRequest id) {
        var a = new PosCostHash();
        a.setId(getId(id.getIdPointA(), id.getIdPointB()));
        a.setIdPointA(id.getIdPointA());
        a.setIdPointB(id.getIdPointB());
        a.setCost(id.getCost());
        return a;
    }

    public static String getId(String id, String idB) {
        return "COST:"+ Stream.of(id, idB).sorted().collect(Collectors.joining(":"));
    }

    @Override
    public PosCostHash delete(String id) {
        cachePosCostRepository.deleteById(id);
        return new PosCostHash();
    }

    @Override
    public PosCostHash putCache(PosCostRequest posHash) {
        var a = getPosCostHash(posHash);
        return cachePosCostRepository.save(a);
    }

    @Override
    public List<PosCostHash> getAll() {
        return StreamUtils.createStreamFromIterator(cachePosCostRepository.findAll().iterator()).toList();
    }

    @Override
    public Optional<PosCostHash> getPointB(String idPointA, String idPointB) {
        return cachePosCostRepository.findById(getId(idPointA, idPointB));
    }

    @Override
    public Optional<PosCostMinHash> getPointMin(String idPointA, String idPointB) {
        return costService.getCost(idPointA, idPointB);
    }
}
