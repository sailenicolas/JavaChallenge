package com.empresa.api.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import com.empresa.api.clients.PosClient;
import com.empresa.api.config.RestConfig;
import com.empresa.api.dtos.requests.PosHashRequest;
import com.empresa.api.dtos.response.PosHash;
import com.empresa.core.dtos.requests.PostHashPutRequest;
import com.github.tomakehurst.wiremock.WireMockServer;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;
import wiremock.org.apache.hc.core5.net.Ports;

@ExtendWith(SpringExtension.class)
@AutoConfigureWireMock(port = 51989)
class CachePosClientServiceImplTest {
    private CachePosClientServiceImpl service;

    @BeforeEach
    void setUp() {
        RestConfig restConfig = new RestConfig();
        RestConfig.GenericUrlConfig cached = new RestConfig.GenericUrlConfig();
        cached.setUrl("http://localhost:%s/ms-pos".formatted(51989));
        restConfig.setPos(cached);
        this.service = new CachePosClientServiceImpl(new PosClient(WebClient.builder(), restConfig));
    }

    @Test
    void findById() {
        PosHash byId = this.service.findById("1");
        assertThat(byId.getPoint()).isNotNull();
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
        List<PosHash> all = this.service.findAll();
        assertThat(all.getFirst().getPoint()).isNotNull();
    }

    @Test
    void update() {
        StepVerifier.create(this.service.update(new PostHashPutRequest(), "hello"))
                .assertNext((a)->{
                    assertThat(a.getPoint()).isNotNull();
                }).verifyComplete();
    }
}