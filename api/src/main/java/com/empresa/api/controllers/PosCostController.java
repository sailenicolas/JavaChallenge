package com.empresa.api.controllers;

import com.empresa.api.dtos.requests.PosCostRequest;
import com.empresa.api.dtos.response.PosCostHash;
import com.empresa.api.dtos.response.PosCostMinHash;
import com.empresa.api.services.CrudExtraService;
import com.empresa.core.controllers.DeleteController;
import com.empresa.core.controllers.GetAllController;
import com.empresa.core.controllers.PostController;
import com.empresa.core.controllers.PutController;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController(value = "/PosCost")
@RequestMapping(path = "/PosCost")
@Validated
public class PosCostController implements DeleteController<PosCostHash>, GetAllController<PosCostHash>, PostController<PosCostHash, PosCostRequest>, PutController<PosCostHash, PosCostPutRequest> {

    private final CrudExtraService<PosCostHash, PosCostRequest> cacheService;

    public PosCostController(CrudExtraService<PosCostHash, PosCostRequest> cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public Mono<ApiResponse<PosCostHash>> delete(String id) {
        return cacheService.delete(id);
    }

    @Override
    public Mono<ApiResponse<List<PosCostHash>>> getAll() {
        return cacheService.getAll();
    }

    @GetMapping(path = "/byId/{idA}/{idB}")
    public Mono<ApiResponse<PosCostHash>> get(@PathVariable(name = "idA") String idA, @PathVariable(name = "idB") String idB) {
        return cacheService.getById(idA, idB);
    }

    @Override
    public Mono<ApiResponse<PosCostHash>> create(PosCostRequest posHash) {
        return cacheService.createCache(posHash);
    }

    @Override
    public Mono<ApiResponse<PosCostHash>> put(PosCostPutRequest posHash, String id) {
        return cacheService.putCache(posHash, id);
    }

    @GetMapping(path = "/pointA")
    public Mono<ApiResponse<List<PosCostBHash>>> getPointA(@RequestParam(name = "idA") String id) {
        return this.cacheService.getPointB(id);
    }

    @GetMapping(path = "/pointMin")
    public Mono<ApiResponse<PosCostMinHash>> getPointMin(@RequestParam(name = "idA") String id, @RequestParam(name = "idB") String idB){
        return this.cacheService.getPointMin(id, idB);
    }

}
