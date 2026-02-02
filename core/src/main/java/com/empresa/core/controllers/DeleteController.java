package com.empresa.core.controllers;

import com.empresa.core.dtos.responses.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

public interface DeleteController<T> {
    @DeleteMapping
    Mono<ApiResponse<T>> delete(@Validated @RequestParam(name = "id") String id);
}
