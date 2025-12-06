package com.empresa.core.controllers;

import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.services.CrudService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Validated
public class CrudController<T, V> {

    private final CrudService<T, V> crudService;

    @GetMapping(path = "/byId/{id}")
    public Mono<ApiResponse<T>> get(@PathVariable(name = "id") String id){
        return crudService.getById(id);
    }
    @GetMapping
    public Mono<ApiResponse<List<T>>> getAll(){
        return crudService.getAll();
    }
    @PostMapping
    public Mono<ApiResponse<T>> create(@Validated @RequestBody V posHash){
        return crudService.createCache(posHash);
    }
    @PutMapping
    public Mono<ApiResponse<T>> put(@RequestBody V posHash, @RequestParam(name = "id") String id){
        return crudService.putCache(posHash, id);
    }
    @DeleteMapping
    public Mono<ApiResponse<T>> delete(@RequestParam(name = "id") String id){
        return crudService.delete(id);
    }


}
