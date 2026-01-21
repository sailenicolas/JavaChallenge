package com.empresa.api.controllers;

import com.empresa.api.dtos.requests.PosHashRequest;
import com.empresa.core.annotations.LogStartClose;
import com.empresa.core.dtos.requests.PostHashPutRequest;
import com.empresa.api.dtos.response.PosHash;
import com.empresa.core.services.CrudService;
import com.empresa.core.controllers.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "Pos")
@RequestMapping("/Pos")
@LogStartClose
public class POSController extends CrudController<PosHash, PosHashRequest, PostHashPutRequest> {

    public POSController(CrudService<PosHash, PosHashRequest, PostHashPutRequest> crudService) {
        super(crudService);
    }
}
