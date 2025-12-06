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
        when(cachePosCostRepository.findById(any())).thenReturn(Optional.of(new CreditsModel()));
        Optional<CreditsModel> credits = service.getById("1");
        assertThat(credits).isPresent();
    }

    @Test
    void createCredits() {
        when(cachePosCostRepository.save(any())).thenReturn(new CreditsModel());
        CreditsModel credits = service.createCredits(new CreditsModel());
        assertThat(credits).isNotNull();
    }
}