package com.empresa.pos.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.empresa.core.dtos.requests.PostHashPutRequest;
import com.empresa.pos.clients.CachePosClient;
import com.empresa.pos.config.RestConfig;
import com.empresa.pos.dtos.requests.PosHashRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@AutoConfigureWireMock(port = 8089)
class CachePosClientServiceImplTest {
    private CachePosClientServiceImpl service;

    @BeforeEach
    void setUp() {
        RestConfig restConfig = new RestConfig();
        RestConfig.GenericUrlConfig cached = new RestConfig.GenericUrlConfig();
        cached.setUrl("http://localhost:8089/ms-cached");
        restConfig.setCached(cached);
        this.service = new CachePosClientServiceImpl(new CachePosClient(WebClient.builder(), restConfig));
    }

    @Test
    void findById() {
        StepVerifier.create(this.service.findById("1"))
                .assertNext((a)->{
                    assertThat(a.getPoint()).isNotNull();
                }).verifyComplete();
    }

    @Test
    void save() {
        StepVerifier.create(this.service.save(new PosHashRequest()))
                .assertNext((a)->{
                    assertThat(a.getPoint()).isNotNull();
                }).verifyComplete();
    }

    @Test
    void deleteById() {
        StepVerifier.create(this.service.deleteById("1"))
                .verifyComplete();
    }

    @Test
    void findAll() {
        StepVerifier.create(this.service.findAll())
                .assertNext((a)->{
                    assertThat(a.getFirst().getPoint()).isNotNull();
                }).verifyComplete();
    }

    @Test
    void update() {
        StepVerifier.create(this.service.update(new PostHashPutRequest(), "hello"))
                .assertNext((a)->{
                    assertThat(a.getPoint()).isNotNull();
                }).verifyComplete();
    }
}