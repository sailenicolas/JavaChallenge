package com.empresa.data.controllers;

import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.data.models.CreditsModel;
import com.empresa.data.services.DataService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/credits")
@AllArgsConstructor
public class DataController {

    private final DataService dataService;

    @GetMapping
    public Mono<ResponseEntity<ApiResponse<CreditsModel>>> getCredits(@RequestParam(name = "id") String id){
        Mono<ApiResponse<CreditsModel>> map = dataService.getById(id).map(ApiResponse::new);
        return map.map(ResponseEntity::ofNullable);
    }

    @PostMapping
    public Mono<ResponseEntity<ApiResponse<CreditsModel>>> postCredits(@RequestBody CreditsModel id){
        Mono<ApiResponse<CreditsModel>> map = dataService.createCredits(id).map(ApiResponse::new);
        return map.map(ResponseEntity::ofNullable);
    }

}
