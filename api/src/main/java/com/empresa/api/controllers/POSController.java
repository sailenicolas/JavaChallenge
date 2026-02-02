package com.empresa.api.controllers;

import com.empresa.api.dtos.requests.PosHashRequest;
import com.empresa.api.dtos.response.PosHash;
import com.empresa.core.annotations.LogStartClose;
import com.empresa.core.dtos.requests.PostHashPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.services.CrudService;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController(value = "Pos")
@RequestMapping("/Pos")
@LogStartClose
public class POSController {

    private final CrudService<PosHash, PosHashRequest, PostHashPutRequest> cacheService;

    public POSController(CrudService<PosHash, PosHashRequest, PostHashPutRequest> crudService) {
        this.cacheService = crudService;
    }

    @DeleteMapping
    public Mono<ApiResponse<PosHash>> delete(@Validated @NotNull Long id) {
        return cacheService.delete(String.valueOf(id));
    }

    @GetMapping
    public Mono<ApiResponse<List<PosHash>>> getAll() {
        return cacheService.getAll();
    }

    @GetMapping(path = "/byId/{idA}")
    public Mono<ApiResponse<PosHash>> get(@Validated @NotNull @PathVariable(name = "idA") Long idA) {
        return cacheService.getById(String.valueOf(idA));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ApiResponse<PosHash>> create(@Validated @RequestBody PosHashRequest posHash) {
        return cacheService.createCache(posHash);
    }

    @PutMapping
    public Mono<ApiResponse<PosHash>> put(@Validated @RequestBody PostHashPutRequest posHash, @Validated @NotNull Long id) {
        return cacheService.putCache(posHash, String.valueOf(id));
    }
}
