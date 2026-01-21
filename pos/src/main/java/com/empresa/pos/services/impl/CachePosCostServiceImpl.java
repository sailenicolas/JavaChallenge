package com.empresa.pos.services.impl;

import static com.empresa.core.utils.DataUtils.getId;

import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.core.exceptions.NotFoundServiceException;
import com.empresa.core.exceptions.ServiceException;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import com.empresa.pos.dtos.response.PosHash;
import com.empresa.pos.services.CacheClientService;
import com.empresa.pos.services.CacheExtraService;
import com.empresa.pos.services.CachePosClientService;
import com.empresa.pos.services.PostCostMinService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
public class CachePosCostServiceImpl implements CacheExtraService<PosCostHash, PosCostRequest> {
    private final CacheClientService clientService;
    private final PostCostMinService postCostMinService;
    private final CachePosClientService posClientService;

    @Override
    public Mono<ApiResponse<PosCostHash>> getById(String id) {
        return clientService.findById(id)
                .map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosCostHash>> createCache(PosCostRequest id) {
        Mono<PosHash> posHashPointA = this.posClientService.findById(id.getIdPointA())
                .publishOn(Schedulers.boundedElastic())
                .subscribeOn(Schedulers.boundedElastic());
        Mono<PosHash> pointB =  this.posClientService.findById(id.getIdPointB())
                .publishOn(Schedulers.boundedElastic())
                .subscribeOn(Schedulers.boundedElastic());
        Mono<PosCostHash> cost = this.clientService.findById(getId("COST", id.getIdPointA(), id.getIdPointB()))
                .onErrorResume(WebClientResponseException.NotFound.class, (a)->Mono.empty())
                .onErrorResume(NotFoundServiceException.class, (e)-> Mono.empty())
                .flatMap(o -> Mono.<PosCostHash>error(new ServiceException("Problematic Call", "Repeated id", HttpStatus.CONFLICT)))
                .publishOn(Schedulers.boundedElastic())
                .subscribeOn(Schedulers.boundedElastic());
        return posHashPointA
                .zipWith(pointB, (a, b)-> new TupleA(a, b, null))
                .zipWith(cost, (a, b)-> new TupleA(a.a(), a.b(), b))
                .publishOn(Schedulers.boundedElastic())
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(o -> Mono.<PosCostHash>empty())
                .switchIfEmpty(clientService.save(id))
                .map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosCostHash>> delete(String id) {
        return clientService.deleteById(id).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosCostHash>> putCache(PosCostPutRequest posHash, String id) {
        return this.clientService.update(posHash, id).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<List<PosCostHash>>> getAll() {
        return clientService.findAll().map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<List<PosCostBHash>>> getPointB(String idPointA) {
        return clientService.getPointB(idPointA)
                .map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosCostMinHash>> getPointMin(String idPointA, String idPointB) {
        return postCostMinService.findById(idPointA, idPointB)
                .map(ApiResponse::new);
    }

    private record TupleA(PosHash a, PosHash b, PosCostHash c) {
    }
}
