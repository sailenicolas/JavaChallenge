package com.empresa.pos.controllers;

import com.empresa.core.dtos.ApiResponse;
import com.empresa.pos.services.CacheExtraService;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.http.HttpMethod;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController(value = "/PosCost")
@RequestMapping(path = "/PosCost")
public class CachePosCostController extends CacheCrudController<PosCostHash, PosCostRequest> {

    private final CacheExtraService<PosCostHash, PosCostRequest> cacheService;

    public CachePosCostController(CacheExtraService<PosCostHash, PosCostRequest> cacheService) {
        super(cacheService);
        this.cacheService = cacheService;
    }

    @GetMapping(path = "/pointB")
    public Mono<ApiResponse<PosCostHash>> getPointB(@RequestParam(name = "id") String id, @RequestParam(name = "idB") String idB){
        return this.cacheService.getPointB(id, idB).map(ApiResponse::new);
    }

    @GetMapping(path = "byIds")
    public Mono<ApiResponse<PosCostHash>> getPointC(@RequestParam(name = "idA") String id,@RequestParam(name = "idB") String idB){
        return this.cacheService.getById("COST:"+Stream.of(id, idB).sorted().collect(Collectors.joining(":"))).map(ApiResponse::new);
    }

    @GetMapping(path = "/pointMin")
    public Mono<ApiResponse<PosCostMinHash>> getPointMin(@RequestParam(name = "id") String id, @RequestParam(name = "idB") String idB){
        return this.cacheService.getPointMin(id, idB).map(ApiResponse::new);
    }

}
