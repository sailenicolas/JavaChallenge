package com.empresa.cache.controllers;

import com.empresa.cache.dtos.requests.PosCostRequest;
import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.model.PosCostMinHash;
import com.empresa.cache.services.CacheExtraService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/PosCost")
@RequestMapping(path = "/PosCost")
public class CachePosCostController extends CacheCrudController<PosCostHash, PosCostRequest> {

    private final CacheExtraService<PosCostHash, PosCostRequest> cacheService;

    public CachePosCostController(CacheExtraService<PosCostHash, PosCostRequest> cacheService) {
        super(cacheService);
        this.cacheService = cacheService;
    }

    @GetMapping(path = "/pointB")
    public ResponseEntity<Optional<PosCostHash>> getPointB(@RequestParam(name = "id") String id, @RequestParam(name = "idB") String idB){
        return ResponseEntity.ofNullable(this.cacheService.getPointB(id, idB));
    }

    @GetMapping(path = "/pointMin")
    public ResponseEntity<Optional<PosCostMinHash>> getPointMin(@RequestParam(name = "id") String id, @RequestParam(name = "idB") String idB){
        return ResponseEntity.ofNullable(this.cacheService.getPointMin(id, idB));
    }
}
