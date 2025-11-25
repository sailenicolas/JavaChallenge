package com.empresa.cache.controllers;

import com.empresa.cache.model.PosHash;
import com.empresa.cache.services.CacheService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "Pos")
@RequestMapping("/Pos")
public class CachePOSController extends CacheCrudController<PosHash, PosHash> {

    public CachePOSController(CacheService<PosHash, PosHash> cacheService) {
        super(cacheService);
    }
}
