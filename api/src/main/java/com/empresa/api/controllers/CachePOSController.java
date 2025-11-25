package com.empresa.api.controllers;

import com.empresa.api.dtos.requests.PosHashRequest;
import com.empresa.api.dtos.response.PosHash;
import com.empresa.api.services.CacheService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "Pos")
@RequestMapping("/Pos")
public class CachePOSController extends CacheCrudController<PosHash, PosHashRequest> {

    public CachePOSController(CacheService<PosHash, PosHashRequest> cacheService) {
        super(cacheService);
    }
}
