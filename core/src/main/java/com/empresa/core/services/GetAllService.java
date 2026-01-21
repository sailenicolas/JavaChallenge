package com.empresa.core.services;

import com.empresa.core.dtos.responses.ApiResponse;
import java.util.List;
import reactor.core.publisher.Mono;

public interface GetAllService<T> {
    Mono<ApiResponse<List<T>>> getAll();
}
