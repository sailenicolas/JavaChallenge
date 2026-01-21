package com.empresa.pos.services.impl;

import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.pos.clients.CachePosCostClient;
import com.empresa.pos.config.RestConfig;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostMinHash;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@AutoConfigureWireMock(port = 9090)
class CacheClientServiceImplTest {
    private CacheClientServiceImpl service;

    @BeforeEach
    void setUp() {
        RestConfig restConfig = new RestConfig();
        RestConfig.GenericUrlConfig cached = new RestConfig.GenericUrlConfig();
        cached.setUrl("http://localhost:9090/ms-cached");
        restConfig.setCached(cached);
        this.service = new CacheClientServiceImpl(new CachePosCostClient(WebClient.builder(), restConfig));
    }

    @Test
    void getPointB() {
        StepVerifier.create(this.service.getPointB("1"))
                .assertNext((a)->{
                    Assertions.assertThat(a.getFirst().getCost()).isNotNull();
                }).verifyComplete();
    }

    @Test
    void save() {
        StepVerifier.create(this.service.save(new PosCostRequest()))
                .assertNext((a)->{
                    Assertions.assertThat(a.getCost()).isNotNull();
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
                    Assertions.assertThat(a.getFirst().getCost()).isNotNull();
                }).verifyComplete();
    }

    @Test
    void getMinCost() {
        StepVerifier.create(this.service.getMinCostBase("1", "2"))
                .assertNext((a)->{
                    Assertions.assertThat(a.incoming().getFirst().getCost()).isNotNull();
                    Assertions.assertThat(a.outgoing().getFirst().getCost()).isNotNull();
                }).verifyComplete();
    }

    @Test
    void findById() {
        StepVerifier.create(this.service.findById("1"))
                .assertNext((a) -> {
                    Assertions.assertThat(a.getCost()).isNotNull();
                    Assertions.assertThat(a.getCost()).isNotNull();
                }).verifyComplete();
    }

    @Test
    void update() {
        StepVerifier.create(this.service.update(new PosCostPutRequest(), "id"))
                .assertNext((a) -> {
                    Assertions.assertThat(a.getCost()).isNotNull();
                    Assertions.assertThat(a.getCost()).isNotNull();
                }).verifyComplete();
    }

}