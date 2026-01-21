package com.empresa.core.services;

import com.empresa.core.dtos.responses.ApiResponse;
import reactor.core.publisher.Mono;

public interface DeleteService<T> {
    Mono<ApiResponse<T>> delete(String id);
}
