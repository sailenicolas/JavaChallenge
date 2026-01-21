package com.empresa.api.services.impl;

import com.empresa.api.clients.PosCostClient;
import com.empresa.api.config.RestConfig;
import com.empresa.api.dtos.requests.PosCostRequest;
import com.empresa.api.dtos.response.PosCostHash;
import com.empresa.api.dtos.response.PosCostMinHash;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.PosCostBHash;
import java.util.List;
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
        cached.setUrl("http://localhost:9090/ms-pos");
        restConfig.setPos(cached);
        this.service = new CacheClientServiceImpl(new PosCostClient(WebClient.builder(), restConfig));
    }

    @Test
    void getPointB() {
        List<PosCostBHash> pointB = this.service.getPointB("1");
        Assertions.assertThat(pointB.getFirst().getCost()).isNotNull();
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
        List<PosCostHash> all = this.service.findAll();
        Assertions.assertThat(all.getFirst().getCost()).isNotNull();
    }

    @Test
    void getMinCost() {
        PosCostMinHash minCost = this.service.getMinCost("1", "2");
        Assertions.assertThat(minCost.getPoints().getFirst().getCost()).isNotNull();
    }

    @Test
    void findById() {
        PosCostHash byId = this.service.findById("1");
        Assertions.assertThat(byId.getCost()).isNotNull();
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