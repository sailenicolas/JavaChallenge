package com.empresa.api.services;

import java.util.List;
import reactor.core.publisher.Mono;

public interface CacheService<T,V> {
    Mono<T> getById(String id);

    Mono<T> createCache(V id);

    Mono<T> delete(String id);

    Mono<T> putCache(V posHash);

    Mono<List<T>> getAll();
}
