package com.empresa.core.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.services.CrudService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CrudControllerTest {
    private CrudService<Object, Object, Object> service;
    private CrudController<Object, Object, Object> controller;

    @BeforeEach
    void setUp() {
        this.service = mock();
        this.controller = new CrudController<>(service,service,service,service,service);
    }

    @Test
    void get() {
        when(this.service.getById(any())).thenReturn(Mono.just(new Object()).map(ApiResponse::new));
        Mono<ApiResponse<Object>> objectMono = this.controller.get("aa");
        StepVerifier.create(objectMono).assertNext((a)->{
            assertThat(a).isNotNull();
        }).verifyComplete();
    }

    @Test
    void getAll() {
        when(this.service.getAll()).thenReturn(Mono.just(Collections.singletonList(new Object())).map(ApiResponse::new));
        Mono<ApiResponse<List<Object>>> objectMono = this.controller.getAll();
        StepVerifier.create(objectMono).assertNext((a)->{
            assertThat(a).isNotNull();
        }).verifyComplete();
    }

    @Test
    void create() {
        when(this.service.createCache(any())).thenReturn(Mono.just(new Object()).map(ApiResponse::new));
        Mono<ApiResponse<Object>> objectMono = this.controller.create(new Object());
        StepVerifier.create(objectMono).assertNext((a)->{
            assertThat(a).isNotNull();
        }).verifyComplete();
    }

    @Test
    void put() {
        when(this.service.putCache(any(), any())).thenReturn(Mono.just(new Object()).map(ApiResponse::new));
        Mono<ApiResponse<Object>> objectMono = this.controller.put(new Object(), "id");
        StepVerifier.create(objectMono).assertNext((a)->{
            assertThat(a).isNotNull();
        }).verifyComplete();
    }

    @Test
    void delete() {
        when(this.service.delete(any())).thenReturn(Mono.just(new Object()).map(ApiResponse::new));
        Mono<ApiResponse<Object>> objectMono = this.controller.delete( "id");
        StepVerifier.create(objectMono).assertNext((a)->{
            assertThat(a).isNotNull();
        }).verifyComplete();
    }
}