package com.empresa.pos.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.core.exceptions.ServiceException;
import com.empresa.pos.dtos.requests.PosCostRequest;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMin;
import com.empresa.pos.dtos.response.PosCostMinHash;
import com.empresa.pos.dtos.response.PosHash;
import com.empresa.pos.services.CacheClientService;
import com.empresa.pos.services.CacheExtraService;
import com.empresa.pos.services.CachePosClientService;
import com.empresa.pos.services.PostCostMinService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class CachePosCostServiceImplTest {
    private CachePosCostServiceImpl posCostService;
    private CacheClientService cacheClientService;
    private PostCostMinService postCostMinService;
    private CachePosClientService posClientService;

    @BeforeEach
    void setUp() {
        cacheClientService = mock();
        postCostMinService = mock();
        posClientService = mock();
        this.posCostService = new CachePosCostServiceImpl(cacheClientService, postCostMinService, posClientService);
    }

    /**
     * Class under test: {@link CachePosCostServiceImpl#getById(String)}
     */
    @Test
    void getById() {
        PosCostHash value = new PosCostHash("", "", "", new BigDecimal("1"));
        value.setCost(new BigDecimal("1"));
        when(this.cacheClientService.findById(any())).thenReturn(Mono.just(value));
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
    void createCache_not_ok() {
        PosCostHash id = new PosCostHash("", "", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cacheClientService.findById(any())).thenReturn(Mono.just(id));
        when(this.posClientService.findById(eq("1"))).thenReturn(Mono.just(new PosHash("1", "1")));
        when(this.posClientService.findById(eq("2"))).thenReturn(Mono.just(new PosHash("2", "2")));
        when(this.cacheClientService.save(any())).thenReturn(Mono.just(id));
        PosCostRequest id1 = new PosCostRequest();
        id1.setCost(new BigDecimal("1"));
        id1.setIdPointA("1");
        id1.setIdPointB("2");
        StepVerifier.create(this.posCostService.createCache(id1))
                .expectErrorSatisfies((o)->{
                    assertThat(o instanceof ServiceException).isTrue();
                    assertThat(((ServiceException)o).getHttpStatusCode()).isEqualTo(HttpStatus.CONFLICT);
                }).verify();
    }
    /**
     * Class under test: {@link CachePosCostServiceImpl#createCache(PosCostRequest)}
     */
    @Test
    void createCache_ok() {
        PosCostHash id = new PosCostHash("", "", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cacheClientService.findById(any())).thenReturn(Mono.empty());
        when(this.posClientService.findById(eq("1"))).thenReturn(Mono.just(new PosHash("1", "1")));
        when(this.posClientService.findById(eq("2"))).thenReturn(Mono.just(new PosHash("2", "2")));
        when(this.cacheClientService.save(any())).thenReturn(Mono.just(id));
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
        when(this.cacheClientService.deleteById(anyString())).thenReturn(Mono.just(new PosCostHash()));
        StepVerifier.create(posCostService.delete("1"))
                .assertNext((o)->{
                    assertThat(o.getData().getCost()).isNull();
                })
                .verifyComplete();

    }

    /**
     * Class under test: {@link CachePosCostServiceImpl#putCache(PosCostPutRequest, String)}
     */
    @Test
    void putCache() {
        PosCostHash id = new PosCostHash("", "", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cacheClientService.update(any(), anyString())).thenReturn(Mono.just(id));
        PosCostPutRequest id1 = new PosCostPutRequest();
        id1.setCost(new BigDecimal("1"));
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
        when(this.cacheClientService.findAll()).thenReturn(Mono.just(new ArrayList<>(Collections.singleton(id))));
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
     * Class under test: {@link CacheExtraService#getPointB(String)}
     */
    @Test
    void getPointB() {
        PosCostBHash id = new PosCostBHash("","", "", "", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.cacheClientService.getPointB(any())).thenReturn(Mono.just(List.of(id)));
        Mono<ApiResponse<List<PosCostBHash>>> pointB = this.posCostService.getPointB("1");
        assertThat(pointB).isNotNull();
        StepVerifier.create(pointB).assertNext((a)->{
            assertThat(a).isNotNull();
        }).verifyComplete();
    }

    @Test
    void getPointMin() {
        PosCostBHash id = new PosCostBHash("", "","","", "", new BigDecimal("1"));
        id.setCost(new BigDecimal("1"));
        id.setIdPointA("1");
        id.setIdPointB("2");
        when(this.postCostMinService.findById(anyString(),anyString())).thenReturn(Mono.just(new PosCostMinHash("", new ArrayList<>(), new BigDecimal("1"))));
        Mono<ApiResponse<PosCostMinHash>> pointB = this.posCostService.getPointMin("1", "2");
        StepVerifier.create(pointB).assertNext((a)->{
            assertThat(a).isNotNull();
        }).verifyComplete();
    }
}