package com.empresa.api.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.empresa.api.dtos.requests.CreditsRequest;
import com.empresa.api.dtos.requests.PosHashRequest;
import com.empresa.api.dtos.response.CreditsResponse;
import com.empresa.api.dtos.response.PosHash;
import com.empresa.api.services.DataClientService;
import com.empresa.api.services.DataService;
import com.empresa.core.dtos.requests.PostHashPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.services.CrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class DataServiceImplTest {
    private DataService service;
    private DataClientService cachePosCostRepository;
    private CrudService<PosHash, PosHashRequest, PostHashPutRequest> clientService;

    @BeforeEach
    void setUp() {
        cachePosCostRepository = mock();
        clientService = mock();
        this.service = new DataServiceImpl(cachePosCostRepository, clientService);
    }

    @Test
    void getById() {
        when(cachePosCostRepository.getById(any(String.class))).thenReturn(new CreditsResponse());
        Mono<CreditsResponse> credits = service.getById("1");
        StepVerifier.create(credits).assertNext((o)->{
            assertThat(o).isNotNull();
        }).verifyComplete();
    }

    @Test
    void createCredits() {
        when(clientService.getById(any())).thenReturn(Mono.just(new ApiResponse<>(new PosHash())));
        when(cachePosCostRepository.create(any())).thenReturn(Mono.just(new CreditsResponse()));
        Mono<CreditsResponse> credits = service.createCredits(new CreditsRequest());
        StepVerifier.create(credits).assertNext((o)->{
            assertThat(o).isNotNull();
        }).verifyComplete();
    }
}