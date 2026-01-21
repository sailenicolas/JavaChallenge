package com.empresa.core.services;

import com.empresa.core.dtos.responses.ApiResponse;
import reactor.core.publisher.Mono;

public interface PutService<T, U> {
    Mono<ApiResponse<T>> putCache(U posHash, String id);
}
