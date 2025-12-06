package com.empresa.cache.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.empresa.cache.dtos.requests.PosHashRequest;
import com.empresa.cache.model.PosHash;
import com.empresa.cache.repositories.CachePosCostRepository;
import com.empresa.cache.repositories.CachePosRepository;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.exceptions.ServiceException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class CrudServiceImplTest {
    private CachePosRepository cachePosCostRepository;
    private CrudServiceImpl service;
    private StringRedisTemplate stringRedisTemplate;

    @BeforeEach
    void setUp() {
        cachePosCostRepository = mock();
        stringRedisTemplate = mock();
        this.service = new CrudServiceImpl(cachePosCostRepository, stringRedisTemplate);
    }

    /**
     * Class under test: {@link CrudServiceImpl#getById(String)}
     */
    @Test
    void getById() {
        PosHash value = new PosHash();
        value.setId("1");
        value.setPoint("2");
        when(cachePosCostRepository.findById(anyString())).thenReturn(Optional.of(value));
        Mono<ApiResponse<PosHash>> byId = this.service.getById("");
        StepVerifier.create(byId).assertNext((a)->{
           assertThat(a.getData().getId()).isEqualTo("1");
           assertThat(a.getData().getPoint()).isEqualTo("2");
        }).verifyComplete();
    }

    /**
     * Class under test: {@link CrudServiceImpl#createCache(PosHashRequest)}
     */
    @Test
    void createCache_ok() {
        ValueOperations<String, String> valueOperations = mock();
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.increment(anyString())).thenReturn(0L);
        when(cachePosCostRepository.findByPointIsIgnoreCase(anyString())).thenReturn(Optional.empty());
        PosHash value = new PosHash();
        value.setId("1");
        value.setPoint("2");
        when(cachePosCostRepository.save(any())).thenReturn(value);
        Mono<ApiResponse<PosHash>> byId = this.service.createCache(new PosHashRequest("1"));
        StepVerifier.create(byId).assertNext((a)->{
            assertThat(a.getData().getId()).isEqualTo("1");
            assertThat(a.getData().getPoint()).isEqualTo("2");
        }).verifyComplete();
    }
    /**
     * Class under test: {@link CrudServiceImpl#createCache(PosHashRequest)}
     */
    @Test
    void createCache_repeated() {
        when(cachePosCostRepository.findByPointIsIgnoreCase(anyString())).thenReturn(Optional.of(new PosHash()));
        PosHash value = new PosHash();
        value.setId("1");
        value.setPoint("2");
        when(cachePosCostRepository.save(any())).thenReturn(value);
        Mono<ApiResponse<PosHash>> byId = this.service.createCache(new PosHashRequest("1"));
        StepVerifier.create(byId)
                .verifyError(ServiceException.class);
    }

    /**
     * Class under test: {@link CrudServiceImpl#delete(String)}
     */
    @Test
    void delete() {
        Mono<ApiResponse<PosHash>> byId = this.service.delete(("1"));
        StepVerifier.create(byId)
                .assertNext((a)->{
                    assertThat(a.getData().getPoint()).isNull();
                }).verifyComplete();

    }

    /**
     * Class under test: {@link CrudServiceImpl#putCache(PosHashRequest, String)}
     */
    @Test
    void putCache() {
        when(cachePosCostRepository.findById(anyString())).thenReturn(Optional.of(new PosHash()));
        PosHash value = new PosHash();
        value.setId("1");
        value.setPoint("2");
        when(cachePosCostRepository.save(any())).thenReturn(value);
        Mono<ApiResponse<PosHash>> byId = this.service.putCache(new PosHashRequest("2"), "1");
        StepVerifier.create(byId)
                .assertNext((a)->{
                    assertThat(a.getData().getPoint()).isEqualTo("2");
                }).verifyComplete();

    }

    /**
     * Class under test: {@link CrudServiceImpl#getAll()}
     */
    @Test
    void getAll() {
        PosHash value = new PosHash();
        value.setId("1");
        value.setPoint("2");
        when(cachePosCostRepository.findAll()).thenReturn(Collections.singleton(value));
        Mono<ApiResponse<List<PosHash>>> byId = this.service.getAll();
        StepVerifier.create(byId)
                .assertNext((a)->{
                    assertThat(a.getData().getFirst().getPoint()).isEqualTo("2");
                }).verifyComplete();
    }
}