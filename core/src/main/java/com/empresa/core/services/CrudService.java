package com.empresa.core.services;

import com.empresa.core.dtos.responses.ApiResponse;
import java.util.List;
import reactor.core.publisher.Mono;

public interface CrudService<T,V> {
    Mono<ApiResponse<T>> getById(String id);

    Mono <ApiResponse<T>> createCache(V id);

    Mono<ApiResponse<T>> delete(String id);

    Mono<ApiResponse<T>> putCache(V posHash, String id);

    Mono<ApiResponse<List<T>>> getAll();
}
