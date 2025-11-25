package com.empresa.api.controllers;

import com.empresa.core.dtos.ApiResponse;
import com.empresa.api.services.CacheService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class CacheCrudController<T, V> {

    private final CacheService<T, V> cacheService;

    @GetMapping(path = "/byId/{id}")
    public Mono<ApiResponse<T>> getCache(@PathVariable(name = "id") String id){
        return cacheService.getById(id).map(ApiResponse::new);
    }
    @GetMapping
    public Mono<ApiResponse<List<T>>> getCacheAll(){
        return cacheService.getAll().map(ApiResponse::new);
    }
    @PostMapping
    public Mono<ApiResponse<T>> createCache(@RequestBody V posHash){
        return cacheService.createCache(posHash).map(ApiResponse::new);
    }
    @PutMapping
    public Mono<ApiResponse<T>> putCache(@RequestBody V posHash){
        return cacheService.putCache(posHash).map(ApiResponse::new);
    }
    @DeleteMapping
    public Mono<ApiResponse<T>> deleteCache(@RequestParam(name = "id") String id){
        return cacheService.delete(id).map(ApiResponse::new);
    }


}
