package com.empresa.core.controllers;

import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.services.DeleteService;
import com.empresa.core.services.GetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

public interface GetController<T> {
    @GetMapping(path = "/byId/{id}")
    Mono<ApiResponse<T>> get(@PathVariable(name = "id") String id);
}
