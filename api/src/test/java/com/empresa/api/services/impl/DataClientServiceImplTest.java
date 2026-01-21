package com.empresa.api.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.empresa.api.clients.CreditsClient;
import com.empresa.api.config.RestConfig;
import com.empresa.api.dtos.requests.CreditsClientRequest;
import com.empresa.api.services.DataClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@AutoConfigureWireMock(port = 8977)
class DataClientServiceImplTest {

    private DataClientService dataClientService;

    @BeforeEach
    void setUp() {

        RestConfig restConfig = new RestConfig();
        restConfig.setData(new RestConfig.GenericUrlConfig("http://localhost:8977/ms-data", true));
        this.dataClientService = new DataClientServiceImpl(new CreditsClient(WebClient.builder(), restConfig));
    }

    @Test
    void getById() {
        var data = this.dataClientService.getById("1");
        assertThat(data).isNotNull();
        assertThat(data.getAmount()).isNotNull();
    }

    @Test
    void create() {
        var data = this.dataClientService.create(new CreditsClientRequest());
        StepVerifier.create(data).assertNext((a)->{
            assertThat(a.getAmount()).isNotNull();
        }).verifyComplete();
    }
}