package com.empresa.cache.controllers;

import com.empresa.cache.dtos.requests.PosCostRequest;
import com.empresa.cache.dtos.response.PosCostMinBase;
import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.services.CrudExtraService;
import com.empresa.core.controllers.CrudController;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/PosCost")
@RequestMapping(path = "/PosCost")
public class PosCostController extends CrudController<PosCostHash, PosCostRequest, PosCostPutRequest> {

    private final CrudExtraService<PosCostHash, PosCostRequest> cacheService;

    public PosCostController(CrudExtraService<PosCostHash, PosCostRequest> cacheService) {
        super(cacheService, cacheService, cacheService, cacheService,cacheService);
        this.cacheService = cacheService;
    }

    @GetMapping(path = "/pointA")
    public ResponseEntity<ApiResponse<List<PosCostBHash>>> getPointB(@RequestParam(name = "idA") String id){
        return ResponseEntity.of(this.cacheService.getPointA(id));
    }

    @GetMapping(path = "/pointMinBase")
    public ResponseEntity<ApiResponse<PosCostMinBase>> getPointMin(@RequestParam(name = "idA") String id, @RequestParam(name = "idB") String idB){
        return ResponseEntity.ofNullable(this.cacheService.getPointMinBase(id, idB));
    }
}
