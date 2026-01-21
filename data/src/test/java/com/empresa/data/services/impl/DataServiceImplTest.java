package com.empresa.data.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.empresa.data.models.CreditsModel;
import com.empresa.data.repositories.DataManagerRepository;
import com.empresa.data.services.DataService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class DataServiceImplTest {
    private DataManagerRepository cachePosCostRepository;
    private DataService service;

    @BeforeEach
    void setUp() {
        cachePosCostRepository = mock();
        this.service = new DataServiceImpl(cachePosCostRepository);
    }

    @Test
    void getById() {
        when(cachePosCostRepository.findById(any(String.class))).thenReturn(Mono.justOrEmpty(new CreditsModel()));
        Mono<CreditsModel> credits = service.getById("1");
        StepVerifier.create(credits).assertNext((o)->{
            assertThat(o).isNotNull();
        }).verifyComplete();
    }

    @Test
    void createCredits() {
        when(cachePosCostRepository.save(any())).thenReturn(Mono.just(new CreditsModel()));
        Mono<CreditsModel> credits = service.createCredits(new CreditsModel());
        StepVerifier.create(credits).assertNext((o)->{
            assertThat(o).isNotNull();
        }).verifyComplete();
    }
}