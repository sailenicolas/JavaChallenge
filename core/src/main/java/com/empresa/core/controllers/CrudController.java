package com.empresa.core.controllers;

import com.empresa.core.annotations.LogStartClose;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.services.CrudService;
import com.empresa.core.services.DeleteService;
import com.empresa.core.services.GetAllService;
import com.empresa.core.services.GetService;
import com.empresa.core.services.PostService;
import com.empresa.core.services.PutService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Validated
@LogStartClose
public class CrudController<T, V, U> implements DeleteController<T>, GetAllController<T>, PostController<T, V>, PutController<T,U>, GetController<T> {

    private final GetService<T> getService;
    private final GetAllService<T> getAllService;
    private final PostService<T, V> postService;
    private final DeleteService<T> deleteService;
    private final PutService<T, U> putService;

    public CrudController(CrudService<T,V,U> crudService) {
    this.postService = crudService;
    this.getService = crudService;
    this.putService = crudService;
    this.getAllService = crudService;
    this.deleteService = crudService;
    }

    @GetMapping
    @Override
    public Mono<ApiResponse<List<T>>> getAll(){
        return getAllService.getAll();
    }
    @GetMapping(path = "/byId/{id}")
    @Override
    public Mono<ApiResponse<T>> get(@PathVariable(name = "id") String id){
        return getService.getById(id);
    }
    @PostMapping
    @Override
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<ApiResponse<T>> create(@Validated @RequestBody V posHash){
        return postService.createCache(posHash);
    }
    @PutMapping
    @Override
    public Mono<ApiResponse<T>> put(@Validated @RequestBody U posHash, @RequestParam(name = "id") String id){
        return putService.putCache(posHash, id);
    }

    @DeleteMapping
    @Override
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<ApiResponse<T>> delete(@RequestParam(name = "id") String id){
        return deleteService.delete(id);
    }


}
