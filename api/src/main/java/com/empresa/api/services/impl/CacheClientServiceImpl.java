package com.empresa.api.services.impl;

import com.empresa.api.clients.PosCostClient;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.api.dtos.requests.PosCostRequest;
import com.empresa.api.dtos.response.PosCostHash;
import com.empresa.api.dtos.response.PosCostMinHash;
import com.empresa.api.services.CacheClientService;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.core.exceptions.NotFoundServiceException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CacheClientServiceImpl implements CacheClientService {
    private final PosCostClient posCostClient;
    @Override
    @Cacheable(value = "pointB", key = "#idPointA")
    public List<PosCostBHash> getPointB(String idPointA) {
        return posCostClient.getPointB(idPointA)
                .map(ApiResponse::getData)
                .blockOptional()
                .orElseThrow(NotFoundServiceException::new);
    }

    @Override
    public Mono<PosCostHash> save(PosCostRequest a) {
        return posCostClient.save(a)
                .map(ApiResponse::getData);
    }

    @Override
    public Mono<PosCostHash> deleteById(String id) {
        return this.posCostClient.deleteById(id)
                .map(ApiResponse::getData);
    }

    @Override
    @Cacheable(value = "findAll", key = "'all'")
    public List<PosCostHash> findAll() {
        return this.posCostClient.findAll()
                .map(ApiResponse::getData).blockOptional().orElseThrow(NotFoundServiceException::new);
    }

    @Override
    @Cacheable(value = "MinCost", key = "#idPointA+'-'+#idPointB")
    public PosCostMinHash getMinCost(String idPointA, String idPointB) {
        return this.posCostClient.getMinCost(idPointA, idPointB)
                .map(ApiResponse::getData)
                .blockOptional()
                .orElseThrow(NotFoundServiceException::new);
    }

    @Override
    @Cacheable(value = "ById", key = "#root.args[0]")
    public PosCostHash findById(String id) {
        return this.posCostClient.findById(id)
                .map(ApiResponse::getData)
                .blockOptional()
                .orElseThrow(NotFoundServiceException::new);
    }

    @Override
    public Mono<PosCostHash> update(PosCostPutRequest posHash, String id) {
        return this.posCostClient.update(posHash, id)
                .map(ApiResponse::getData);
    }
}
