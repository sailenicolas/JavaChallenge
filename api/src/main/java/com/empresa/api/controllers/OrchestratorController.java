package com.empresa.api.controllers;

import com.empresa.api.dtos.requests.CreditsRequest;
import com.empresa.api.dtos.response.CreditsResponse;
import com.empresa.api.services.DataService;
import com.empresa.core.dtos.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController("Credits")
@RequestMapping(path = "/Credits")
@AllArgsConstructor
public class OrchestratorController {

    private final DataService dataService;

    @GetMapping
    public Mono<ApiResponse<CreditsResponse>> getCredits(@RequestParam(name = "id") String id){
        return dataService.getById(id).map(ApiResponse::new);
    }

    @PostMapping
    public Mono<ApiResponse<CreditsResponse>> postCredits(@RequestBody CreditsRequest id){
        System.out.println("id = " + id.getPointId());
        return dataService.createCredits(id).map(ApiResponse::new);
    }

}
