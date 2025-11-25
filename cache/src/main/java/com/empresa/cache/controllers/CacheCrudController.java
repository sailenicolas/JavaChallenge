package com.empresa.cache.controllers;

import com.empresa.cache.services.CacheService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
public class CacheCrudController<T, V> {

    private final CacheService<T, V> cacheService;

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity<T> getCache(@PathVariable(name = "id") String id){
        return ResponseEntity.of(cacheService.getById(id));
    }
    @GetMapping
    public ResponseEntity<List<T>> getCacheAll(){
        List<T> all = cacheService.getAll();
        return ResponseEntity.ofNullable(all);
    }
    @PostMapping
    public ResponseEntity<T> createCache(@RequestBody V posHash){
        return ResponseEntity.ofNullable(cacheService.createCache(posHash));
    }
    @PutMapping
    public ResponseEntity<T> putCache(@RequestBody V posHash){
        return ResponseEntity.ofNullable(cacheService.putCache(posHash));
    }
    @DeleteMapping
    public ResponseEntity<T> deleteCache(@RequestParam(name = "id") String id){
        return ResponseEntity.ofNullable(cacheService.delete(id));
    }


}
