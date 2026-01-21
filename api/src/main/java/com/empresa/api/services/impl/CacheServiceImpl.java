package com.empresa.api.services.impl;

import com.empresa.core.dtos.requests.PostHashPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.api.dtos.requests.PosHashRequest;
import com.empresa.api.dtos.response.PosHash;
import com.empresa.api.services.CachePosClientService;
import com.empresa.core.services.CrudService;
import java.util.List;
import java.util.concurrent.Executors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
public class CacheServiceImpl implements CrudService<PosHash, PosHashRequest, PostHashPutRequest> {
    public static final Scheduler SCHEDULER = Schedulers.fromExecutorService(Executors.newVirtualThreadPerTaskExecutor());
    private final CachePosClientService cachePosRepository;

    @Override
    public Mono<ApiResponse<PosHash>> getById(String id) {
        return Mono.fromCallable(()->cachePosRepository.findById(id))
                .publishOn(SCHEDULER)
                .subscribeOn(SCHEDULER).map(ApiResponse::new);
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
    public Mono<ApiResponse<PosHash>> putCache(PostHashPutRequest posHash, String id) {
        return cachePosRepository.update(posHash, id).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<List<PosHash>>> getAll() {
        return Mono.fromCallable(cachePosRepository::findAll)
                .subscribeOn(Schedulers.boundedElastic())
                .map(ApiResponse::new);
    }
}
