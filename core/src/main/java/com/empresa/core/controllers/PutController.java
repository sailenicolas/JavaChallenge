package com.empresa.core.controllers;

import com.empresa.core.dtos.responses.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

public interface PutController<T, V> {
    @PutMapping
    Mono<ApiResponse<T>> put(@Validated @RequestBody V posHash, @RequestParam(name = "id") String id);

}
