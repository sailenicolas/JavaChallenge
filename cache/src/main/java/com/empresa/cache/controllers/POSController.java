package com.empresa.cache.controllers;

import com.empresa.cache.dtos.requests.PosHashRequest;
import com.empresa.cache.model.PosHash;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.requests.PostHashPutRequest;
import com.empresa.core.services.CrudService;
import com.empresa.core.controllers.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "Pos")
@RequestMapping("/Pos")
public class POSController extends CrudController<PosHash, PosHashRequest, PostHashPutRequest> {

    public POSController(CrudService<PosHash, PosHashRequest, PostHashPutRequest> crudService) {
        super(crudService, crudService, crudService, crudService, crudService);
    }
}
