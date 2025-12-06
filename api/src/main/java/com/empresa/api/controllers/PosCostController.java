package com.empresa.pos.controllers;

import com.empresa.core.controllers.CrudController;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import com.empresa.pos.services.CrudExtraService;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController(value = "/PosCost")
@RequestMapping(path = "/PosCost")
@Validated
public class PosCostController extends CrudController<PosCostHash, PosCostRequest> {

    private final CrudExtraService<PosCostHash, PosCostRequest> cacheService;

    public PosCostController(CrudExtraService<PosCostHash, PosCostRequest> cacheService) {
        super(cacheService);
        this.cacheService = cacheService;
    }

    @GetMapping(path = "/pointB")
    public Mono<ApiResponse<PosCostHash>> getPointB(@RequestParam(name = "id") String id, @RequestParam(name = "idB") String idB){
        return this.cacheService.getPointB(id, idB)
                .map(ApiResponse::new);
    }

    @GetMapping(path = "byIds")
    public Mono<ApiResponse<PosCostHash>> getPointC(@RequestParam(name = "idA") String id,@RequestParam(name = "idB") String idB){
        return this.cacheService.getById(getId(id, idB))
                .map(ApiResponse::new);
    }

    private static String getId(String id, String idB) {
        return "COST:" + Stream.of(id, idB).sorted().collect(Collectors.joining(":"));
    }

    @GetMapping(path = "/pointMin")
    public Mono<ApiResponse<PosCostMinHash>> getPointMin(@RequestParam(name = "id") String id, @RequestParam(name = "idB") String idB){
        return this.cacheService.getPointMin(id, idB).map(ApiResponse::new);
    }

}
