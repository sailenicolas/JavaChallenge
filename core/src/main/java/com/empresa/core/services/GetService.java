package com.empresa.core.services;

import com.empresa.core.dtos.responses.ApiResponse;
import reactor.core.publisher.Mono;

public interface GetService<T> {
    Mono<ApiResponse<T>> getById(String id);
}
