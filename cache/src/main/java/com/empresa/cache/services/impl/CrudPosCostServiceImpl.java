package com.empresa.cache.services.impl;

import static com.empresa.core.utils.DataUtils.getId;

import com.empresa.cache.dtos.requests.PosCostRequest;
import com.empresa.cache.dtos.response.PosCostMinBase;
import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.model.PosHash;
import com.empresa.cache.repositories.CachePosCostRepository;
import com.empresa.cache.repositories.CachePosRepository;
import com.empresa.cache.services.CrudExtraService;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.core.exceptions.NotFoundServiceException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
public class CrudPosCostServiceImpl implements CrudExtraService<PosCostHash, PosCostRequest> {
    public static final String COST = "COST";
    private final CachePosRepository cachePosRepository;
    private final CachePosCostRepository cachePosCostRepository;

    @Override
    public Mono<ApiResponse<PosCostHash>> getById(String id) {
        return Mono.fromSupplier(
                        () -> cachePosCostRepository.findById(id).orElse(null))
                .switchIfEmpty(Mono.error(new NotFoundServiceException()))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ApiResponse::new);
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
    public Mono<ApiResponse<PosCostHash>> putCache(PosCostPutRequest posHash, String id) {
        Mono<ApiResponse<PosCostHash>> posCostHashMono = getById(id)
                .switchIfEmpty(Mono.error(new NotFoundServiceException()))
                .flatMap( o-> {
                    o.getData().setCost(posHash.getCost());
                    return Mono.fromSupplier(()-> this.cachePosCostRepository.save(o.getData()))
                            .subscribeOn(Schedulers.boundedElastic())
                            .publishOn(Schedulers.boundedElastic());
                }).map(ApiResponse::new);
        return posCostHashMono
                .publishOn(Schedulers.boundedElastic())
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<ApiResponse<List<PosCostHash>>> getAll() {
        return Mono.fromSupplier(() -> StreamUtils
                        .createStreamFromIterator(cachePosCostRepository
                                .findAll()
                                .iterator())
                        .toList())
                .subscribeOn(Schedulers.boundedElastic())
                .map(ApiResponse::new);
    }

    @Override
    public Optional<ApiResponse<List<PosCostBHash>>> getPointA(String idPointA) {
        return Optional.of(new ApiResponse<>(getListApiResponse(idPointA, () -> Stream.of(cachePosCostRepository.findAllByIdPointB(idPointA)).flatMap(List::parallelStream).toList())));
    }

    private List<PosCostBHash> getListApiResponse(String idPoint, Supplier<? extends List<PosCostHash>> oall) {
        PosHash point = cachePosRepository
                .findById(idPoint)
                .orElseThrow(NotFoundServiceException::new);
        var id = CompletableFuture
                .supplyAsync(oall)
                .thenApplyAsync((w)->w
                        .parallelStream()
                        .map(o-> CompletableFuture
                                .supplyAsync(()-> getIds(w))
                                .thenApplyAsync(this.cachePosRepository::findAllById)
                                .thenApplyAsync(t -> {
                    PosHash posCostHashA = getPosCostHashA(point, t);
                    PosHash posCostHashB = getPosCostHashB(o, t);
                    return new PosCostBHash(o.getId(), o.getIdPointA(), posCostHashA.getPoint(), o.getIdPointB(), posCostHashB.getPoint(), o.getCost());
                                }))
                        .toList());

        var array = id.join().parallelStream().toArray(CompletableFuture[]::new);
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(array);
        voidCompletableFuture.join();
        return Arrays.stream(array).parallel().map(CrudPosCostServiceImpl::getCostBHash).toList();
    }

    @SneakyThrows
    private static PosCostBHash getCostBHash(CompletableFuture<PosCostBHash> o) {
        return o.get();
    }

    private static Set<String> getIds(List<PosCostHash> allByIdPointA) {
        var idsAll = allByIdPointA.parallelStream().map(PosCostHash::getIdPointA)
                .collect(Collectors.toSet());
        idsAll.addAll(allByIdPointA.parallelStream().map(PosCostHash::getIdPointB).collect(Collectors.toSet()));
        return idsAll;
    }

    private static PosHash getPosCostHashA(PosHash o, Iterable<PosHash> allById) {
        return StreamUtils.createStreamFromIterator(allById.iterator()).filter(i -> i.getId().equalsIgnoreCase(o.getId())).findFirst().orElse(null);
    }
    private static PosHash getPosCostHashB(PosCostHash o, Iterable<PosHash> allById) {
        PosHash posHash = StreamUtils.createStreamFromIterator(allById.iterator())
                .filter(i -> i.getId().equalsIgnoreCase(o.getIdPointB()))
                .findFirst()
                .orElse(null);
        if (posHash == null)
            System.out.printf("posHash = 000 %s%n", o.getIdPointA() + o.getIdPointB());
        return posHash;
    }

    @Override
    public ApiResponse<PosCostMinBase> getPointMinBase(String idPointA, String idPointB) {
        List<PosCostBHash> outgoing = this.getListApiResponse(idPointA, ()-> Stream.of(cachePosCostRepository.findAllByIdPointAOrIdPointA(idPointA,idPointB)).flatMap(List::parallelStream
        ).collect(Collectors.toSet()).stream().toList());
        List<PosCostBHash> incoming = this.getListApiResponse(idPointB, ()-> Stream.of(cachePosCostRepository.findAllByIdPointBOrIdPointB(idPointA,idPointB)).flatMap(List::parallelStream
        ).collect(Collectors.toSet()).stream().toList());
        return new ApiResponse<>(new PosCostMinBase(outgoing, incoming));
    }
}
