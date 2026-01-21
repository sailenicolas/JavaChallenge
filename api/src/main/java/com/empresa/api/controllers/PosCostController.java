package com.empresa.api.controllers;

import static com.empresa.core.utils.DataUtils.getId;

import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.controllers.CrudController;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.api.dtos.requests.PosCostRequest;
import com.empresa.api.dtos.response.PosCostHash;
import com.empresa.api.dtos.response.PosCostMinHash;
import com.empresa.api.services.CrudExtraService;
import com.empresa.core.dtos.responses.PosCostBHash;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController(value = "/PosCost")
@RequestMapping(path = "/PosCost")
@Validated
public class PosCostController extends CrudController<PosCostHash, PosCostRequest, PosCostPutRequest> {

    private final CrudExtraService<PosCostHash, PosCostRequest> cacheService;

    public PosCostController(CrudExtraService<PosCostHash, PosCostRequest> cacheService) {
        super(cacheService,cacheService,cacheService,cacheService,cacheService);
        this.cacheService = cacheService;
    }

    @GetMapping(path = "/pointA")
    public Mono<ApiResponse<List<PosCostBHash>>> getPointC(@RequestParam(name = "idA") String id){
        return this.cacheService.getPointB(id);
    }

    @GetMapping(path = "/pointMin")
    public Mono<ApiResponse<PosCostMinHash>> getPointMin(@RequestParam(name = "idA") String id, @RequestParam(name = "idB") String idB){
        return this.cacheService.getPointMin(id, idB);
    }

}
