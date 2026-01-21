package com.empresa.api.services.impl;

import com.empresa.api.clients.PosClient;
import com.empresa.api.dtos.requests.PosHashRequest;
import com.empresa.core.dtos.requests.PostHashPutRequest;
import com.empresa.api.dtos.response.PosHash;
import com.empresa.api.services.CachePosClientService;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.exceptions.NotFoundServiceException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CachePosClientServiceImpl implements CachePosClientService {
    private final PosClient posClient;
    @Override
    @Cacheable(value = "pos", key = "#root.args[0]", unless = "#result == null")
    public PosHash findById(String id) {
        return posClient.findById(id)
                .map(ApiResponse::getData)
                .blockOptional()
                .orElseThrow(NotFoundServiceException::new);
    }

    @Override
    public Mono<PosHash> save(PosHashRequest id) {
        return posClient.save(id).map(ApiResponse::getData);
    }

    @Override
    public Mono<PosHash> deleteById(String id) {
        return this.posClient.deleteById(id).map(ApiResponse::getData);

    }

    @Override
    @Cacheable(value = "posAll", key = "'all'")
    public List<PosHash> findAll() {
        return this.posClient.findAll().map(ApiResponse::getData).blockOptional().orElseThrow();
    }

    @Override
    public Mono<PosHash> update(PostHashPutRequest posHash, String id) {
        return this.posClient.update(posHash, id).map(ApiResponse::getData);
    }
}
