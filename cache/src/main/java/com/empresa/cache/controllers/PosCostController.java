package com.empresa.cache.controllers;

import com.empresa.cache.dtos.requests.PosCostRequest;
import com.empresa.cache.dtos.requests.PostCostMinRequest;
import com.empresa.cache.dtos.response.PosCostMin;
import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.model.PosCostMinHash;
import com.empresa.cache.services.CrudExtraService;
import com.empresa.core.controllers.CrudController;
import com.empresa.core.dtos.responses.ApiResponse;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/PosCost")
@RequestMapping(path = "/PosCost")
public class PosCostController extends CrudController<PosCostHash, PosCostRequest> {

    private final CrudExtraService<PosCostHash, PosCostRequest> cacheService;

    public PosCostController(CrudExtraService<PosCostHash, PosCostRequest> cacheService) {
        super(cacheService);
        this.cacheService = cacheService;
    }

    @GetMapping(path = "/pointB")
    public ResponseEntity<ApiResponse<PosCostHash>> getPointB(@RequestParam(name = "id") String id, @RequestParam(name = "idB") String idB){
        return ResponseEntity.of(this.cacheService.getPointB(id, idB));
    }

    @GetMapping(path = "/pointMin")
    public ResponseEntity<ApiResponse<PosCostMin>> getPointsMin(@RequestParam(name = "id") String id, @RequestParam(name = "idB") String idB){
        return ResponseEntity.ofNullable(this.cacheService.getPointsMin(id, idB));
    }
    @PostMapping(path = "/pointMin")
    public ResponseEntity<ApiResponse<PosCostMinHash>> postPointMin(@RequestBody PostCostMinRequest postCostMinRequest){
        return ResponseEntity.ofNullable(this.cacheService.postCostMin(postCostMinRequest));
    }
    @GetMapping(path = "/pointMinBase")
    public ResponseEntity<ApiResponse<PosCostMinHash>> postPointBase(@RequestParam(name = "idA") String id, @RequestParam(name = "idB") String idB){
        return ResponseEntity.of(this.cacheService.getPointMinBase(id, idB));
    }
}
