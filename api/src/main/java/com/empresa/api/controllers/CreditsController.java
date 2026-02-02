package com.empresa.api.controllers;

import com.empresa.api.dtos.requests.CreditsRequest;
import com.empresa.api.dtos.response.CreditsResponse;
import com.empresa.api.services.DataService;
import com.empresa.core.annotations.LogStartClose;
import com.empresa.core.dtos.responses.ApiResponse;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController("Credits")
@RequestMapping(path = "/Credits")
@AllArgsConstructor
@LogStartClose
@Validated
public class CreditsController {

    private final DataService dataService;

    @GetMapping
    public Mono<ApiResponse<CreditsResponse>> getCredits(@Validated @NotEmpty @RequestParam(name = "id") String id) {
        return dataService.getById(id).map(ApiResponse::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ApiResponse<CreditsResponse>> postCredits(@Validated @RequestBody CreditsRequest id) {
        return dataService.createCredits(id).map(ApiResponse::new);
    }

}
