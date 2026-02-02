package com.empresa.api.controllers;

import com.empresa.api.dtos.requests.PosCostRequest;
import com.empresa.api.dtos.response.PosCostHash;
import com.empresa.api.dtos.response.PosCostMinHash;
import com.empresa.api.services.CrudExtraService;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController(value = "/PosCost")
@RequestMapping(path = "/PosCost")
@Validated
public class PosCostController {

    private final CrudExtraService<PosCostHash, PosCostRequest> cacheService;

    public PosCostController(CrudExtraService<PosCostHash, PosCostRequest> cacheService) {
        this.cacheService = cacheService;
    }

    @DeleteMapping
    public Mono<ApiResponse<PosCostHash>> delete(@Validated @Pattern(regexp = "^COST:\\d+:\\d+$") String id) {
        return cacheService.delete(id);
    }

    @GetMapping
    public Mono<ApiResponse<List<PosCostHash>>> getAll() {
        return cacheService.getAll();
    }

    @GetMapping(path = "/byId/{idA}/{idB}")
    public Mono<ApiResponse<PosCostHash>> get(@Validated @NotNull @PathVariable(name = "idA") Long idA, @PathVariable(name = "idB") @Validated @NotNull Long idB) {
        return cacheService.getById(String.valueOf(idA), String.valueOf(idB));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ApiResponse<PosCostHash>> create(@Validated @RequestBody PosCostRequest posHash) {
        return cacheService.createCache(posHash);
    }

    @PutMapping
    public Mono<ApiResponse<PosCostHash>> put(@Validated @RequestBody PosCostPutRequest posHash, @Validated @Pattern(regexp = "^COST:\\d+:\\d+$") String id) {
        return cacheService.putCache(posHash, id);
    }

    @GetMapping(path = "/pointA")
    public Mono<ApiResponse<List<PosCostBHash>>> getPointA(@Validated @NotNull @RequestParam(name = "idA") Long id) {
        return this.cacheService.getPointB(String.valueOf(id));
    }

    @GetMapping(path = "/pointMin")
    public Mono<ApiResponse<PosCostMinHash>> getPointMin(@Validated @RequestParam(name = "idA") Long id, @RequestParam(name = "idB") Long idB) {
        return this.cacheService.getPointMin(String.valueOf(id), String.valueOf(idB));
    }

}
