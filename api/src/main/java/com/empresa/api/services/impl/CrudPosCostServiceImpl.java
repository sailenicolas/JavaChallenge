package com.empresa.api.services.impl;

import static reactor.core.scheduler.Schedulers.boundedElastic;

import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.api.dtos.requests.PosCostRequest;
import com.empresa.api.dtos.response.PosCostHash;
import com.empresa.api.dtos.response.PosCostMinHash;
import com.empresa.api.services.CacheClientService;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.api.services.CrudExtraService;
import com.empresa.core.dtos.responses.PosCostBHash;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
@AllArgsConstructor
public class CrudPosCostServiceImpl implements CrudExtraService<PosCostHash, PosCostRequest> {
    public static final Scheduler SCHEDULER = boundedElastic();
    private final CacheClientService clientService;

    @Override
    public Mono<ApiResponse<PosCostHash>> getById(String id) {
        return Mono.fromCallable(()->clientService.findById(id))
                .subscribeOn(SCHEDULER)
                .map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosCostHash>> createCache(PosCostRequest posHash) {
            Mono<PosCostHash> save = clientService.save(posHash);
            return save.map(ApiResponse::new);

    }

    @Override
    public Mono<ApiResponse<PosCostHash>> delete(String id) {
        return clientService.deleteById(id).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosCostHash>> putCache(PosCostPutRequest posHash, String id) {
        return clientService.update(posHash, id).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<List<PosCostHash>>> getAll() {
        return Mono.fromSupplier(clientService::findAll)
                .subscribeOn(SCHEDULER).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<List<PosCostBHash>>> getPointB(String idPointA) {
        return Mono.fromSupplier(()->clientService.getPointB(idPointA))
                .subscribeOn(SCHEDULER).map(ApiResponse::new);
    }

    @Override
    public  Mono<ApiResponse<PosCostMinHash>> getPointMin(String idPointA, String idPointB) {
        return Mono.fromSupplier(()->clientService.getMinCost(idPointA, idPointB)).subscribeOn(SCHEDULER).map(ApiResponse::new);
    }
}
