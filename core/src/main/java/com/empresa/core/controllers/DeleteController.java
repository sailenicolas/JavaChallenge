package com.empresa.core.controllers;

import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.services.CrudService;
import com.empresa.core.services.DeleteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

public interface DeleteController<T> {
    @DeleteMapping
     Mono<ApiResponse<T>> delete(@RequestParam(name = "id") String id);
}
