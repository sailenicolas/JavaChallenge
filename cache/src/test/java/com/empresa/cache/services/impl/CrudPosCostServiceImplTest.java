package com.empresa.cache.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.empresa.cache.dtos.requests.PosCostRequest;
import com.empresa.cache.dtos.requests.PostCostMinRequest;
import com.empresa.cache.dtos.response.PosCostMin;
import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.model.PosCostMinHash;
import com.empresa.cache.repositories.CachePosCostMinRepository;
import com.empresa.cache.repositories.CachePosCostRepository;
import com.empresa.core.dtos.responses.ApiResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class CrudPosCostServiceImplTest {
    private CrudPosCostServiceImpl posCostService;
    private CachePosCostRepository cachePosCostRepository;
    private CachePosCostMinRepository cachePosCostMinRepository;

    @BeforeEach
    void setUp() {
         cachePosCostRepository = mock();
         cachePosCostMinRepository = mock();
        this.posCostService = new CrudPosCostServiceImpl(cachePosCostRepository, cachePosCostMinRepository);
    }

    /**
     * Class under test: {@link CrudPosCostServiceImpl#getById(String)}
     */
    @Test
    void getById() {
        PosCostHash value = new PosCostHash();
        value.setCost(new BigDecimal("1"));
        when(this.cachePosCostRepository.findById(any())).thenReturn(Optional.of(value));
        StepVerifier.create(this.posCostService.getById("a"))
                .assertNext((a)->{
                    assertThat(a.getData().getCost()).isEqualTo(new BigDecimal("1"));
                })
                .verifyComplete();
    }

    /**
     * Class under test: {@link CrudPosCostServiceImpl#createCache(PosCostRequest)}
     */
    @Test
    void createCache() {
        PosCostHash id = new PosCostHash();
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.save(any())).thenReturn(id);
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
     * Class under test: {@link CrudPosCostServiceImpl#delete(String)}
     */
    @Test
    void delete() {
        StepVerifier.create(posCostService.delete("1"))
                .assertNext((o)->{
                    assertThat(o.getData().getCost()).isNull();
                })
                .verifyComplete();

    }

    /**
     * Class under test: {@link CrudPosCostServiceImpl#putCache(PosCostRequest, String)}
     */
    @Test
    void putCache() {
        PosCostHash id = new PosCostHash();
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.save(any())).thenReturn(id);
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
     * Class under test: {@link CrudPosCostServiceImpl#getById(String)}
     */
    @Test
    void getAll() {
        PosCostHash id = new PosCostHash();
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.findAll()).thenReturn(new ArrayList<>(Collections.singleton(id)));
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
     * Class under test: {@link CrudPosCostServiceImpl#getPointB(String, String)}
     */
    @Test
    void getPointB() {
        PosCostHash id = new PosCostHash();
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.findById(any())).thenReturn(Optional.of(id));
        Optional<ApiResponse<PosCostHash>> pointB = this.posCostService.getPointB("1", "2");
        assertThat(pointB).isNotNull();
    }

    /**
     * Class under test: {@link CrudPosCostServiceImpl#getPointsMin(String, String)}
     */
    @Test
    void getPointsMin() {
        PosCostHash id = new PosCostHash();
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.findAllByIdPointA(any())).thenReturn(Collections.singletonList(id));
        when(this.cachePosCostRepository.findAllByIdPointB(any())).thenReturn(Collections.singletonList(id));
        PosCostMin pointB = this.posCostService.getPointsMin("1", "2").getData();
        assertThat(pointB.incoming().size()).isEqualTo(1);
        assertThat(pointB.outgoing().size()).isEqualTo(1);
    }

    /**
     * Class under test: {@link CrudPosCostServiceImpl#postCostMin(PostCostMinRequest)}
     */
    @Test
    void postCostMin() {
        PosCostHash id = new PosCostHash();
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        PosCostMinHash value = new PosCostMinHash();
        value.setId("id");
        value.setMinTotalCost(new BigDecimal("1"));
        value.setPoints(Collections.singletonList(id));
        when(this.cachePosCostMinRepository.save(any())).thenReturn(value);
        PostCostMinRequest postCostMinRequest = new PostCostMinRequest(Collections.singletonList(id), new BigDecimal("1"), "", "");
        PosCostMinHash pointB = this.posCostService.postCostMin(postCostMinRequest).getData();
        assertThat(pointB.getMinTotalCost()).isEqualTo(new BigDecimal("1"));

    }

    /**
     * Class under test: {@link CrudPosCostServiceImpl#getPointMinBase(String, String)}
     */
    @Test
    void getPointMinBase() {
        PosCostHash id = new PosCostHash();
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        PosCostMinHash value = new PosCostMinHash();
        value.setId("id");
        value.setMinTotalCost(new BigDecimal("1"));
        value.setPoints(Collections.singletonList(id));
        Optional<PosCostMinHash> id1 = Optional.of(value);
        when(this.cachePosCostMinRepository.findById(any())).thenReturn(id1);
        Optional<ApiResponse<PosCostMinHash>> pointB = this.posCostService.getPointMinBase("a", "a");
        assertThat(pointB).isPresent();
        assertThat(pointB.get().getData().getMinTotalCost()).isEqualTo(new BigDecimal("1"));
    }
}