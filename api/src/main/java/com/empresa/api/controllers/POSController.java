package com.empresa.pos.controllers;

import com.empresa.pos.dtos.requests.PosHashRequest;
import com.empresa.pos.dtos.response.PosHash;
import com.empresa.core.services.CrudService;
import com.empresa.core.controllers.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "Pos")
@RequestMapping("/Pos")
public class POSController extends CrudController<PosHash, PosHashRequest> {

    public POSController(CrudService<PosHash, PosHashRequest> crudService) {
        super(crudService);
    }
}
