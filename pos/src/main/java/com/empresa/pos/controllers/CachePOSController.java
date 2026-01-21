package com.empresa.pos.controllers;

import com.empresa.core.controllers.CrudController;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.services.CrudService;
import com.empresa.pos.dtos.requests.PosHashRequest;
import com.empresa.pos.dtos.response.PosHash;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "Pos")
@RequestMapping("/Pos")
public class CachePOSController extends CrudController<PosHash, PosHashRequest, PosCostPutRequest> {

    public CachePOSController(CrudService<PosHash, PosHashRequest, PosCostPutRequest> cacheService) {
        super(cacheService,cacheService,cacheService,cacheService,cacheService);
    }
}
