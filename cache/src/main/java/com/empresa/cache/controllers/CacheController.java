package com.empresa.cache.controllers;

import com.empresa.cache.dtos.response.GetResponse;
import com.empresa.cache.services.CacheService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CacheController {

    private final CacheService cacheService;

    @GetMapping
    public ResponseEntity<GetResponse> getCache(@RequestParam String id){
        return ResponseEntity.ofNullable(cacheService.getById(id));
    }

}
