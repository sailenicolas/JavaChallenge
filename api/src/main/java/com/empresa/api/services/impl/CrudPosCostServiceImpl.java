package com.empresa.pos.services.impl;

import static com.empresa.core.utils.DataUtils.getId;

import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import com.empresa.pos.dtos.response.PosHash;
import com.empresa.pos.services.CacheClientService;
import com.empresa.pos.services.CrudExtraService;
import com.empresa.pos.services.CachePosClientService;
import com.empresa.core.exceptions.NotFoundServiceException;
import com.empresa.core.exceptions.ServiceException;
import java.util.List;
import java.util.concurrent.Executors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
public class CrudPosCostServiceImpl implements CrudExtraService<PosCostHash, PosCostRequest> {
    public static final Scheduler SCHEDULER = Schedulers.fromExecutorService(Executors.newVirtualThreadPerTaskExecutor());
    private final CacheClientService clientService;
    private final CachePosClientService cachePosService;

    @Override
    public Mono<PosCostHash> getById(String id) {
        return Mono.fromCallable(()->clientService.findById(id))
                .subscribeOn(SCHEDULER);
    }

    @Override
    public Mono<PosCostHash> createCache(PosCostRequest id) {
        Mono<PosHash> posHashPointA = Mono
                .fromCallable(() -> this.cachePosService.findById(id.getIdPointA()))
                .publishOn(SCHEDULER)
                .subscribeOn(SCHEDULER);
        Mono<PosHash> pointB = Mono.fromCallable(() -> this.cachePosService.findById(id.getIdPointB()))
                .publishOn(SCHEDULER)
                .subscribeOn(SCHEDULER);
        Mono<PosCostHash> cost = Mono.fromCallable(() -> this.clientService.findById(getId("COST", id.getIdPointA(), id.getIdPointB())))
                .onErrorResume(WebClientResponseException.NotFound.class, (a)->Mono.empty())
                .onErrorResume(NotFoundServiceException.class, (e)-> Mono.empty())
                .flatMap(o -> Mono.<PosCostHash>error(new ServiceException("Problematic Call", "Repeated id", HttpStatus.CONFLICT)))
                .publishOn(SCHEDULER)
                .subscribeOn(SCHEDULER);
        return posHashPointA
                .zipWith(pointB)
                .zipWith(cost)
                .publishOn(SCHEDULER)
                .subscribeOn(SCHEDULER)
                .flatMap(o -> Mono.<PosCostHash>empty())
                .switchIfEmpty(cost)
                .switchIfEmpty(clientService.save(id));
    }


    @Override
    public Mono<PosCostHash> delete(String id) {
        return clientService.deleteById(id);
    }

    @Override
    public Mono<PosCostHash> putCache(PosCostRequest posHash, String id) {
        return clientService.update(posHash);
    }

    @Override
    public Mono<List<PosCostHash>> getAll() {
        return Mono.fromCallable(clientService::findAll)
                .subscribeOn(SCHEDULER);
    }

    @Override
    public Mono<PosCostHash> getPointB(String idPointA, String idPointB) {
        return Mono.fromCallable(()->clientService.getPointB(idPointA, idPointB)).subscribeOn(SCHEDULER);
    }

    @Override
    public  Mono<PosCostMinHash> getPointMin(String idPointA, String idPointB) {
        return Mono.fromCallable(()->clientService.getMinCost(idPointA, idPointB)).subscribeOn(SCHEDULER);
    }
}
