package com.empresa.pos.controllers;

import static com.empresa.core.utils.DataUtils.getId;

import com.empresa.core.controllers.CrudController;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import com.empresa.pos.services.CacheExtraService;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController(value = "/PosCost")
@RequestMapping(path = "/PosCost")
public class CachePosCostController extends CrudController<PosCostHash, PosCostRequest, PosCostPutRequest> {

    private final CacheExtraService<PosCostHash, PosCostRequest> cacheService;

    public CachePosCostController(CacheExtraService<PosCostHash, PosCostRequest> cacheService) {
        super(cacheService,cacheService,cacheService,cacheService,cacheService);
        this.cacheService = cacheService;
    }

    @GetMapping(path = "/pointA")
    public Mono<ApiResponse<List<PosCostBHash>>> getPointB(@RequestParam(name = "idPointA") String id){
        return this.cacheService.getPointB(id);
    }

    @GetMapping(path = "byIds")
    public Mono<ApiResponse<PosCostHash>> getPointC(@RequestParam(name = "idA") String idA,@RequestParam(name = "idB") String idB){
        return this.cacheService.getById(getId("COST", idA, idB));
    }

    @GetMapping(path = "/pointMin")
    public Mono<ApiResponse<PosCostMinHash>> getPointMin(@RequestParam(name = "idA") String id, @RequestParam(name = "idB") String idB){
        return this.cacheService.getPointMin(id, idB);
    }

}
