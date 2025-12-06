package com.empresa.pos.services.impl;

import com.empresa.pos.clients.CachePosClient;
import com.empresa.pos.config.RestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(SpringExtension.class)
@AutoConfigureWireMock(port = 8089)
class CachePosClientServiceImplTest {
    private CachePosClientServiceImpl service;

    @BeforeEach
    void setUp() {
        RestConfig restConfig = new RestConfig();
        RestConfig.GenericUrlConfig cached = new RestConfig.GenericUrlConfig();
        cached.setUrl("http:/localhost:8089/ms-cached");
        restConfig.setCached(cached);
        this.service = new CachePosClientServiceImpl(new CachePosClient(WebClient.builder(), restConfig));
    }

    @Test
    void findById() {

    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }
}