package com.empresa.core.controllers;

import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.services.CrudService;
import com.empresa.core.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface PostController<T,V> {

    @PostMapping
    public Mono<ApiResponse<T>> create(@Validated @RequestBody V posHash);
}
