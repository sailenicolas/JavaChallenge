package com.empresa.pos.controllers;

import com.empresa.core.controllers.CrudController;
import com.empresa.core.dtos.requests.PostHashPutRequest;
import com.empresa.core.services.CrudService;
import com.empresa.pos.dtos.requests.PosHashRequest;
import com.empresa.pos.dtos.response.PosHash;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "Pos")
@RequestMapping("/Pos")
public class CachePOSController extends CrudController<PosHash, PosHashRequest, PostHashPutRequest> {

    public CachePOSController(CrudService<PosHash, PosHashRequest, PostHashPutRequest> cacheService) {
        super(cacheService,cacheService,cacheService,cacheService,cacheService);
    }
}
