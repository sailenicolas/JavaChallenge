package com.empresa.core.controllers;

import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.services.GetAllService;
import com.empresa.core.services.GetService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

public interface GetAllController<T> {
    @GetMapping
     Mono<ApiResponse<List<T>>> getAll();
}
