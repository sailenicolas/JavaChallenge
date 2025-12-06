package com.empresa.pos.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMin;
import com.empresa.pos.services.CacheClientService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class CachePosCostServiceImplTest {
    private CachePosCostServiceImpl posCostService;
    private CacheClientService cachePosCostRepository;

    @BeforeEach
    void setUp() {
        cachePosCostRepository = mock();
        this.posCostService = new CachePosCostServiceImpl(cachePosCostRepository);
    }

    /**
     * Class under test: {@link CachePosCostServiceImpl#getById(String)}
     */
    @Test
    void getById() {
        PosCostHash value = new PosCostHash("", "", "", new BigDecimal("1"));
        value.setCost(new BigDecimal("1"));
        when(this.cachePosCostRepository.findById(any())).thenReturn(Mono.just(value));
        StepVerifier.create(this.posCostService.getById("a"))
                .assertNext((a)->{
                    assertThat(a.getData().getCost()).isEqualTo(new BigDecimal("1"));
                })
                .verifyComplete();
    }

    /**
     * Class under test: {@link CachePosCostServiceImpl#createCache(PosCostRequest)}
     */
    @Test
    void createCache() {
        PosCostHash id = new PosCostHash("", "", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.save(any())).thenReturn(Mono.just(id));
        PosCostRequest id1 = new PosCostRequest();
        id1.setCost(new BigDecimal("1"));
        id1.setIdPointA("1");
        id1.setIdPointB("2");
        StepVerifier.create(this.posCostService.createCache(id1))
                .assertNext((o)->{
                    assertThat(o.getData().getCost()).isEqualTo(id.getCost());
                })
                .verifyComplete();
    }

    /**
     * Class under test: {@link CachePosCostServiceImpl#delete(String)}
     */
    @Test
    void delete() {
        when(this.cachePosCostRepository.deleteById(anyString())).thenReturn(Mono.just(new PosCostHash()));
        StepVerifier.create(posCostService.delete("1"))
                .assertNext((o)->{
                    assertThat(o.getData().getCost()).isNull();
                })
                .verifyComplete();

    }

    /**
     * Class under test: {@link CachePosCostServiceImpl#putCache(PosCostRequest, String)}
     */
    @Test
    void putCache() {
        PosCostHash id = new PosCostHash("", "", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.update(any())).thenReturn(Mono.just(id));
        PosCostRequest id1 = new PosCostRequest();
        id1.setCost(new BigDecimal("1"));
        id1.setIdPointA("1");
        id1.setIdPointB("2");
        StepVerifier.create(this.posCostService.putCache(id1, "1"))
                .assertNext((o)->{
                    assertThat(o.getData().getCost()).isEqualTo(id.getCost());
                })
                .verifyComplete();
    }

    /**
     * Class under test: {@link CachePosCostServiceImpl#getById(String)}
     */
    @Test
    void getAll() {
        PosCostHash id = new PosCostHash("", "", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.findAll()).thenReturn(Mono.just(new ArrayList<>(Collections.singleton(id))));
        PosCostRequest id1 = new PosCostRequest();
        id1.setCost(new BigDecimal("1"));
        id1.setIdPointA("1");
        id1.setIdPointB("2");
        StepVerifier.create(this.posCostService.getAll())
                .assertNext((o)->{
                    assertThat(o.getData().getFirst().getCost()).isEqualTo(id.getCost());
                })
                .verifyComplete();
    }

    /**
     * Class under test: {@link CachePosCostServiceImpl#getPointB(String, String)}
     */
    @Test
    void getPointB() {
        PosCostHash id = new PosCostHash("", "", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.getPointB(any(), anyString())).thenReturn(Mono.just(id));
        Mono<PosCostHash> pointB = this.posCostService.getPointB("1", "2");
        assertThat(pointB).isNotNull();
    }

    @Test
    void getPointMin() {
        PosCostHash id = new PosCostHash("", "", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.getMinCost(any(), anyString())).thenReturn(Mono.just(new PosCostMin(Collections.singletonList(id), Collections.singletonList(id))));
        Mono<PosCostMin> pointB = this.posCostService.getPointMin("1", "2");
        assertThat(pointB).isNotNull();
    }
}