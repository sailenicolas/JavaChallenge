package com.empresa.pos.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.empresa.core.dtos.requests.PostHashPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.pos.dtos.requests.PosHashRequest;
import com.empresa.pos.dtos.response.PosHash;
import com.empresa.pos.services.CachePosClientService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class CrudServiceImplTest {
    private CacheServiceImpl service;
    private CachePosClientService cachePosCostRepository;

    @BeforeEach
    void setUp() {
        cachePosCostRepository = mock();
        this.service = new CacheServiceImpl(cachePosCostRepository);
    }

    /**
     * Class under test: {@link CacheServiceImpl#getById(String)}
     */
    @Test
    void getById() {
        PosHash value = new PosHash();
        value.setId("1");
        value.setPoint("2");
        when(cachePosCostRepository.findById(anyString())).thenReturn(Mono.just(value));
        Mono<ApiResponse<PosHash>> byId = this.service.getById("");
        StepVerifier.create(byId).assertNext((a)->{
           assertThat(a.getData().getId()).isEqualTo("1");
           assertThat(a.getData().getPoint()).isEqualTo("2");
        }).verifyComplete();
    }

    /**
     * Class under test: {@link CacheServiceImpl#createCache(PosHashRequest)}
     */
    @Test
    void createCache_ok() {
        PosHash value = new PosHash();
        value.setId("1");
        value.setPoint("2");
        when(cachePosCostRepository.save(any())).thenReturn(Mono.just(value));
        Mono<ApiResponse<PosHash>> byId = this.service.createCache(new PosHashRequest("1"));
        StepVerifier.create(byId).assertNext((a)->{
            assertThat(a.getData().getId()).isEqualTo("1");
            assertThat(a.getData().getPoint()).isEqualTo("2");
        }).verifyComplete();
    }

    /**
     * Class under test: {@link CacheServiceImpl#delete(String)}
     */
    @Test
    void delete() {
        when(cachePosCostRepository.deleteById(anyString())).thenReturn(Mono.just(new PosHash()));
        Mono<ApiResponse<PosHash>> byId = this.service.delete(("1"));
        StepVerifier.create(byId)
                .assertNext((a)->{
                    assertThat(a.getData().getPoint()).isNull();
                }).verifyComplete();

    }

    /**
     * Class under test: {@link CacheServiceImpl#putCache(PostHashPutRequest, String)}
     */
    @Test
    void putCache() {
        when(cachePosCostRepository.findById(anyString())).thenReturn(Mono.just(new PosHash()));
        PosHash value = new PosHash();
        value.setId("1");
        value.setPoint("2");
        when(cachePosCostRepository.update(any(), any())).thenReturn(Mono.just(value));
        Mono<ApiResponse<PosHash>> byId = this.service.putCache(new PostHashPutRequest("1"), "1");
        StepVerifier.create(byId)
                .assertNext((a)->{
                    assertThat(a.getData().getPoint()).isEqualTo("2");
                }).verifyComplete();

    }

    /**
     * Class under test: {@link CacheServiceImpl#getAll()}
     */
    @Test
    void getAll() {
        PosHash value = new PosHash();
        value.setId("1");
        value.setPoint("2");
        var just = Mono.just(List.of(value));
        when(cachePosCostRepository.findAll()).thenReturn(just);
        Mono<ApiResponse<List<PosHash>>> byId = this.service.getAll();
        StepVerifier.create(byId)
                .assertNext((a)->{
                    assertThat(a.getData().getFirst().getPoint()).isEqualTo("2");
                }).verifyComplete();
    }
}