package com.empresa.api.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.empresa.api.dtos.requests.PosCostRequest;
import com.empresa.api.dtos.response.PosCostHash;
import com.empresa.api.dtos.response.PosCostMinHash;
import com.empresa.api.services.CacheClientService;
import com.empresa.api.services.CrudExtraService;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class CrudPosCostServiceImplTest {
    private CacheClientService cachePosCostRepository;
    private CrudPosCostServiceImpl posCostService;

    @BeforeEach
    void setUp() {
        cachePosCostRepository = mock();
        this.posCostService = new CrudPosCostServiceImpl(cachePosCostRepository);
    }

    /**
     * Class under test: {@link CrudPosCostServiceImpl#getById(String)}
     */
    @Test
    void getById() {
        PosCostHash value = new PosCostHash("", "", "", new BigDecimal("1"));
        value.setCost(new BigDecimal("1"));
        when(this.cachePosCostRepository.findById(any())).thenReturn(value);
        StepVerifier.create(this.posCostService.getById("a"))
                .assertNext((a) -> {
                    assertThat(a.getData().getCost()).isEqualTo(new BigDecimal("1"));
                })
                .verifyComplete();
    }


    /**
     * Class under test: {@link CrudPosCostServiceImpl#createCache(PosCostRequest)}
     */
    @Test
    void createCache_ok() {
        PosCostHash id = new PosCostHash("", "", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.findById(any())).thenReturn(null);
        when(this.cachePosCostRepository.save(any())).thenReturn(Mono.just(id));
        PosCostRequest id1 = new PosCostRequest();
        id1.setCost(new BigDecimal("1"));
        id1.setIdPointA("1");
        id1.setIdPointB("2");
        StepVerifier.create(this.posCostService.createCache(id1))
                .assertNext((o) -> {
                    assertThat(o.getData().getCost()).isEqualTo(id.getCost());
                })
                .verifyComplete();
    }

    /**
     * Class under test: {@link CrudPosCostServiceImpl#delete(String)}
     */
    @Test
    void delete() {
        when(this.cachePosCostRepository.deleteById(anyString())).thenReturn(Mono.just(new PosCostHash()));
        StepVerifier.create(posCostService.delete("1"))
                .assertNext((o) -> {
                    assertThat(o.getData().getCost()).isNull();
                })
                .verifyComplete();

    }

    /**
     * Class under test: {@link CrudPosCostServiceImpl#putCache(PosCostPutRequest, String)}
     */
    @Test
    void putCache() {
        PosCostHash id = new PosCostHash("", "", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.update(any(), anyString())).thenReturn(Mono.just(id));
        PosCostPutRequest id1 = new PosCostPutRequest();
        id1.setCost(new BigDecimal("1"));
        StepVerifier.create(this.posCostService.putCache(id1, "1"))
                .assertNext((o) -> {
                    assertThat(o.getData().getCost()).isEqualTo(id.getCost());
                })
                .verifyComplete();
    }

    /**
     * Class under test: {@link CrudPosCostServiceImpl#getById(String)}
     */
    @Test
    void getAll() {
        PosCostHash id = new PosCostHash("", "", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.findAll()).thenReturn(new ArrayList<>(Collections.singleton(id)));
        PosCostRequest id1 = new PosCostRequest();
        id1.setCost(new BigDecimal("1"));
        id1.setIdPointA("1");
        id1.setIdPointB("2");
        StepVerifier.create(this.posCostService.getAll())
                .assertNext((o) -> {
                    assertThat(o.getData().getFirst().getCost()).isEqualTo(id.getCost());
                })
                .verifyComplete();
    }

    /**
     * Class under test: {@link CrudExtraService#getPointB(String)}
     */
    @Test
    void getPointB() {
        PosCostBHash id = new PosCostBHash("", "", "", "", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.getPointB(any())).thenReturn(List.of(id));
        Mono<ApiResponse<List<PosCostBHash>>> pointB = this.posCostService.getPointB("1");
        assertThat(pointB).isNotNull();
    }

    @Test
    void getPointMin() {
        var id = new PosCostBHash("", "", "", "", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.getMinCost(any(), anyString())).thenReturn(new PosCostMinHash("1", Collections.singletonList(id), new BigDecimal("1")));
        Mono<ApiResponse<PosCostMinHash>> pointB = this.posCostService.getPointMin("1", "2");
        assertThat(pointB).isNotNull();
    }

    @Test
    void getByIdB() {
        when(this.cachePosCostRepository.findById(any())).thenReturn(new PosCostHash("1", "id", "", new BigDecimal("1")));
        Mono<ApiResponse<PosCostHash>> point = this.posCostService.getById("1", "2");
        assertThat(point).isNotNull();

    }
}