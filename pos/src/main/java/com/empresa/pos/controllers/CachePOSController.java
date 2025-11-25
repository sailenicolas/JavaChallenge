package com.empresa.pos.controllers;

import com.empresa.pos.dtos.requests.PosHashRequest;
import com.empresa.pos.dtos.response.PosHash;
import com.empresa.pos.services.CacheService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "Pos")
@RequestMapping("/Pos")
public class CachePOSController extends CacheCrudController<PosHash, PosHashRequest> {

    public CachePOSController(CacheService<PosHash, PosHashRequest> cacheService) {
        super(cacheService);
    }
}
