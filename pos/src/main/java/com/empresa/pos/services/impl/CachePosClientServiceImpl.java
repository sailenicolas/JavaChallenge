package com.empresa.pos.services.impl;

import com.empresa.pos.clients.CachePosClient;
import com.empresa.pos.dtos.requests.PosHashRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosHash;
import com.empresa.pos.services.CachePosClientService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CachePosClientServiceImpl implements CachePosClientService {
    private final CachePosClient cachePosClient;
    @Override
    public Mono<PosHash> findById(String id) {
        return cachePosClient.findById(id);
    }

    @Override
    public Mono<PosHash> save(PosHashRequest id) {
        return cachePosClient.save(id);
    }

    @Override
    public Mono<PosHash> deleteById(String id) {
        return this.cachePosClient.deleteById(id);

    }

    @Override
    public Mono<List<PosHash>> findAll() {
        return this.cachePosClient.findAll();
    }

    @Override
    public Mono<PosHash> update(PosHashRequest posHash) {
        return this.cachePosClient.update(posHash);
    }
}
