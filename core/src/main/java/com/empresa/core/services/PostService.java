package com.empresa.core.services;

import com.empresa.core.dtos.responses.ApiResponse;
import reactor.core.publisher.Mono;

public interface PostService<T, V> {
    Mono<ApiResponse<T>> createCache(V posHash);
}
