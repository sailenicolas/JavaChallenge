package com.empresa.cache.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.empresa.cache.dtos.requests.PosCostRequest;
import com.empresa.cache.dtos.response.PosCostMinBase;
import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.model.PosHash;
import com.empresa.cache.repositories.CachePosCostRepository;
import com.empresa.cache.repositories.CachePosRepository;
import com.empresa.cache.services.CrudExtraService;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class CrudPosCostServiceImplTest {
    private CrudPosCostServiceImpl posCostService;
    private CachePosCostRepository cachePosCostRepository;
    private CachePosRepository cachePosRepository;

    @BeforeEach
    void setUp() {
         cachePosCostRepository = mock();
        cachePosRepository = mock();
        this.posCostService = new CrudPosCostServiceImpl(cachePosRepository,cachePosCostRepository);
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
     * Class under test: {@link CrudPosCostServiceImpl#putCache(PosCostPutRequest, String)} 
     */
    @Test
    void putCache() {
        PosCostHash id = new PosCostHash();
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cachePosCostRepository.save(any())).thenReturn(id);
        when(this.cachePosCostRepository.findById(any())).thenReturn(Optional.of(id));
        PosCostPutRequest id1 = new PosCostPutRequest();
        id1.setCost(new BigDecimal("1"));
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
     * Class under test: {@link CrudExtraService#getPointA(String)}
     */
    @Test
    void getPointB() {
        PosCostHash id = new PosCostHash();
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        List<PosCostHash> ida = List.of(id);
        PosHash posHash = new PosHash();
        posHash.setPoint("hello");
        posHash.setId("1");
        PosHash posHash1 = new PosHash();
        posHash1.setPoint("hello");
        posHash1.setId("2");
        when(this.cachePosRepository.findById(any())).thenReturn(Optional.of(posHash));
        when(this.cachePosRepository.findAllById(any())).thenReturn(List.of(posHash1, posHash));
        when(this.cachePosCostRepository.findAllByIdPointA(any())).thenReturn(ida);
        Optional<ApiResponse<List<PosCostBHash>>> pointB = this.posCostService.getPointA("1");
        List<PosCostBHash> data = assertThat(pointB).isPresent().get().actual().getData();
        assertThat(data).isNotNull();
    }

    /**
     * Class under test: {@link CrudPosCostServiceImpl#getPointMinBase(String, String)}
     */
    @Test
    void getPointsMin() {
        PosCostHash id = new PosCostHash();
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        PosCostHash id1 = new PosCostHash();
        id1.setCost(new BigDecimal("5"));
        id1.setIdPointA("3");
        id1.setIdPointB("4");
        PosHash posHash = new PosHash();
        posHash.setId("1");
        posHash.setPoint("hello");
        PosHash posHash1 = new PosHash();
        posHash1.setId("2");
        posHash1.setPoint("hello");
        PosHash posHash2 = new PosHash();
        posHash2.setId("3");
        posHash2.setPoint("hello");
        PosHash posHash3 = new PosHash();
        posHash3.setId("4");
        posHash3.setPoint("hello");
        PosHash posHash4 = new PosHash();
        posHash4.setId("5");
        posHash4.setPoint("hello");
        when(this.cachePosRepository.findById(any())).thenReturn(Optional.of(posHash));
        Iterable<PosHash> aa = List.of(posHash,posHash1,posHash2,posHash3,posHash4);
        when(this.cachePosRepository.findAllById(any())).thenReturn(aa);
        when(this.cachePosCostRepository.findAllByIdPointAOrIdPointA(any(),any())).thenReturn(Collections.singletonList(id));
        when(this.cachePosCostRepository.findAllByIdPointBOrIdPointB(any(),any())).thenReturn(Collections.singletonList(id1));
        PosCostMinBase pointB = this.posCostService.getPointMinBase("1", "2").getData();
        assertThat(pointB.incoming().size()).isEqualTo(1);
        assertThat(pointB.outgoing().size()).isEqualTo(1);
    }

}