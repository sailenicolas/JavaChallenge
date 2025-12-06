package com.empresa.cache.services.impl;

import com.empresa.cache.dtos.requests.PosHashRequest;
import com.empresa.cache.model.PosHash;
import com.empresa.cache.repositories.CachePosRepository;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.exceptions.NotFoundServiceException;
import com.empresa.core.exceptions.ServiceException;
import com.empresa.core.services.CrudService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
public class CrudServiceImpl implements CrudService<PosHash, PosHashRequest> {
    private final CachePosRepository cachePosRepository;
    private final StringRedisTemplate redisTemplate;
    @Override
    public Mono<ApiResponse<PosHash>> getById(String id) {
        return Mono.fromSupplier(()->cachePosRepository.findById(id).orElse(null)).subscribeOn(Schedulers.boundedElastic()).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosHash>> createCache(PosHashRequest id) {
        Mono<PosHash> posHashMono = Mono.fromSupplier(() -> cachePosRepository.findByPointIsIgnoreCase(id.getPoint()).orElse(null))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(o-> Mono.error(new ServiceException("Not Possible to save due to errors", "Already Present", HttpStatus.CONFLICT)));
        return posHashMono.switchIfEmpty(Mono.fromSupplier(()->{
            ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
            Long increment = stringStringValueOperations.increment("pos:id", 1L);
            var newCache = new PosHash();
            newCache.setId("%s".formatted(increment));
            newCache.setPoint(id.getPoint());
            return cachePosRepository.save(newCache);
        }).subscribeOn(Schedulers.boundedElastic())).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosHash>> delete(String id) {
        return Mono.<PosHash>fromRunnable(()->cachePosRepository.deleteById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .then(Mono.defer(() -> Mono.just(new PosHash())))
                .subscribeOn(Schedulers.boundedElastic()).map(ApiResponse::new);
    }

    @Override
    public Mono<ApiResponse<PosHash>> putCache(PosHashRequest posHash, String id) {
        Mono<ApiResponse<PosHash>> posHashMono = this.getById(id)
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new NotFoundServiceException()));
        return posHashMono.flatMap(o->{
            var newCache = new PosHash();
            newCache.setPoint(posHash.getPoint());
            newCache.setId(id);
            return Mono.just(newCache);
        }).flatMap(o -> Mono.fromSupplier(() -> cachePosRepository.save(o)).map(ApiResponse::new)
                .subscribeOn(Schedulers.boundedElastic()));
    }

    @Override
    public Mono<ApiResponse<List<PosHash>>> getAll() {
        return Mono.fromSupplier(()->StreamUtils.createStreamFromIterator(cachePosRepository.findAll().iterator()).toList()).subscribeOn(Schedulers.boundedElastic()).map(ApiResponse::new);
    }
}
