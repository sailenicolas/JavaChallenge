package com.empresa.pos.services.impl;

import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import com.empresa.pos.services.CacheClientService;
import com.empresa.pos.services.CacheExtraService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CachePosCostServiceImpl implements CacheExtraService<PosCostHash, PosCostRequest> {
    private final CacheClientService clientService;

    @Override
    public Mono<PosCostHash> getById(String id) {
        return clientService.findById(id);
    }

    @Override
    public Mono<PosCostHash> createCache(PosCostRequest id) {
        return clientService.save(id);
    }


    @Override
    public Mono<PosCostHash> delete(String id) {
        return clientService.deleteById(id);
    }

    @Override
    public Mono<PosCostHash> putCache(PosCostRequest posHash) {
        return clientService.update(posHash);
    }

    @Override
    public Mono<List<PosCostHash>> getAll() {
        return clientService.findAll();
    }

    @Override
    public Mono<PosCostHash> getPointB(String idPointA, String idPointB) {
        return clientService.getPointB(idPointA, idPointB);
    }

    @Override
    public  Mono<PosCostMinHash> getPointMin(String idPointA, String idPointB) {
        return clientService.getMinCost(idPointA, idPointB);
    }
}
