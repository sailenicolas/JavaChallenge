package com.empresa.cache.services.impl;

import static com.empresa.core.utils.DataUtils.getId;

import com.empresa.cache.dtos.requests.PosCostRequest;
import com.empresa.cache.dtos.requests.PostCostMinRequest;
import com.empresa.cache.dtos.response.PosCostMin;
import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.model.PosCostMinHash;
import com.empresa.cache.repositories.CachePosCostMinRepository;
import com.empresa.cache.repositories.CachePosCostRepository;
import com.empresa.cache.services.CrudExtraService;
import com.empresa.core.dtos.responses.ApiResponse;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
public class CrudPosCostServiceImpl implements CrudExtraService<PosCostHash, PosCostRequest> {
    public static final String COST = "COST";
    private final CachePosCostRepository cachePosCostRepository;
    private final CachePosCostMinRepository cachePosCostMinRepository;

    @Override
    public Mono<ApiResponse<PosCostHash>> getById(String id) {
        return Mono.fromSupplier(()->cachePosCostRepository.findById(id)
                        .orElse(null)
                ).subscribeOn(Schedulers.boundedElastic()).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosCostHash>> createCache(PosCostRequest id) {
        var a = getPosCostHash(id);
        return Mono.fromSupplier(()->cachePosCostRepository.save(a))
                .publishOn(Schedulers.boundedElastic())
                .subscribeOn(Schedulers.boundedElastic()).map(ApiResponse::new);
    }

    private static PosCostHash getPosCostHash(PosCostRequest id) {
        var a = new PosCostHash();
        a.setId(getId(COST, id.getIdPointA(), id.getIdPointB()));
        a.setIdPointA(id.getIdPointA());
        a.setIdPointB(id.getIdPointB());
        a.setCost(id.getCost());
        return a;
    }
    @Override
    public Mono<ApiResponse<PosCostHash>> delete(String id) {
        Mono.fromRunnable(()->cachePosCostRepository.deleteById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
        return Mono.just(new PosCostHash()).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosCostHash>> putCache(PosCostRequest posHash, String id) {
        var a = getPosCostHash(posHash);
        Mono<ApiResponse<PosCostHash>> posCostHashMono = getById(id)
                .publishOn(Schedulers.boundedElastic())
                .subscribeOn(Schedulers.boundedElastic());
        return posCostHashMono.switchIfEmpty(Mono.fromSupplier(()->cachePosCostRepository.save(a)).publishOn(Schedulers.boundedElastic())
                .subscribeOn(Schedulers.boundedElastic()).map(ApiResponse::new));
    }

    @Override
    public Mono<ApiResponse<List<PosCostHash>>> getAll() {
        return Mono.fromSupplier(()->StreamUtils.createStreamFromIterator(cachePosCostRepository.findAll().iterator()).toList()).subscribeOn(Schedulers.boundedElastic()).map(ApiResponse::new);
    }

    @Override
    public Optional<ApiResponse<PosCostHash>> getPointB(String idPointA, String idPointB) {
        return cachePosCostRepository.findById(getId(COST,idPointA, idPointB)).map(ApiResponse::new);
    }

    @Override
    public ApiResponse<PosCostMin> getPointsMin(String idPointA, String idPointB) {
        List<PosCostHash> outgoing = cachePosCostRepository.findAllByIdPointA(idPointA);
        List<PosCostHash> incoming = cachePosCostRepository.findAllByIdPointB(idPointB);
        return new ApiResponse<>(new PosCostMin(outgoing, incoming));
    }

    @Override
    public ApiResponse<PosCostMinHash> postCostMin(PostCostMinRequest postCostMinRequest) {
        PosCostMinHash entity = new PosCostMinHash(postCostMinRequest, getId("MINTCOST", postCostMinRequest.getIdPointA(), postCostMinRequest.getIdPointB()));
        return new ApiResponse<>(cachePosCostMinRepository.save(entity));
    }

    @Override
    public Optional<ApiResponse<PosCostMinHash>> getPointMinBase(String id, String idB) {
        return cachePosCostMinRepository.findById(getId("MINTCOST", id, idB)).map(ApiResponse::new);
    }
}
