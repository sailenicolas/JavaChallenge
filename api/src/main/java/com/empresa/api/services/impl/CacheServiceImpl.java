package com.empresa.pos.services.impl;

import com.empresa.pos.dtos.requests.PosHashRequest;
import com.empresa.pos.dtos.response.PosHash;
import com.empresa.pos.services.CachePosClientService;
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
public class CacheServiceImpl implements CrudService<PosHash, PosHashRequest> {
    public static final Scheduler SCHEDULER = Schedulers.fromExecutorService(Executors.newVirtualThreadPerTaskExecutor());
    private final CachePosClientService cachePosRepository;

    @Override
    public Mono<PosHash> getById(String id) {
        return Mono.fromCallable(()->cachePosRepository.findById(id))
                .publishOn(SCHEDULER)
                .subscribeOn(SCHEDULER);
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
    public Mono<PosHash> putCache(PosHashRequest posHash, String id) {
        return cachePosRepository.update(posHash);
    }

    @Override
    public Mono<List<PosHash>> getAll() {
        return Mono.fromCallable(cachePosRepository::findAll)
                .subscribeOn(Schedulers.fromExecutorService(Executors.newVirtualThreadPerTaskExecutor()));
    }
}
