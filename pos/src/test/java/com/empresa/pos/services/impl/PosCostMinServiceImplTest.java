package com.empresa.pos.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMin;
import com.empresa.pos.dtos.response.PosCostMinHash;
import com.empresa.pos.services.CacheClientService;
import com.empresa.pos.services.PostCostMinService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class PosCostMinServiceImplTest {

    private PosCostMinServiceImpl service;
    private GraphService graphService;
    private CacheClientService costService;

    @BeforeEach
    void setUp() {
        graphService = mock();
        costService = mock();
        this.service = new PosCostMinServiceImpl(graphService, costService);
    }

    @Test
    void findById() {
        ArrayList<PosCostBHash> outgoing = new ArrayList<>(List.of(new PosCostBHash("1", "1", "1", "2", "1", new BigDecimal("1"))));
        ArrayList<PosCostBHash> incoming = new ArrayList<>(List.of(new PosCostBHash("1", "2", "1", "3", "2", new BigDecimal("1"))));
        when(this.costService.getMinCostBase(any(), anyString())).thenReturn(Mono.just(new PosCostMin(outgoing, incoming)));
        GraphPath<String, DefaultWeightedEdge> mock = mock();
        when(this.graphService.shortestPath(anyString(), anyString(), any())).thenReturn(mock);
        when(mock.getEdgeList()).thenReturn(new ArrayList<>());
      var find =  this.service.findById("1", "3");
        StepVerifier.create(find).assertNext(posCostMinHash -> {
            assertThat(posCostMinHash.getPoints()).isEmpty();
        }).verifyComplete();
    }

    @Test
    void shortestPath() {
        GraphPath<String, DefaultWeightedEdge> mock = mock();
        when(this.graphService.shortestPath(anyString(), anyString(), any())).thenReturn(mock);
        DefaultWeightedEdge defaultWeightedEdge = new DefaultWeightedEdge() ;
        Graph<String, DefaultWeightedEdge> edgeGraph = mock();
        when(mock.getGraph()).thenReturn(edgeGraph);
        when(edgeGraph.getEdgeSource(any())).thenReturn("1");
        when(edgeGraph.getEdgeTarget(any())).thenReturn("2");
        when(edgeGraph.getEdgeWeight(any())).thenReturn(1.1D);
        when(mock.getEdgeList()).thenReturn(new ArrayList<>(List.of(defaultWeightedEdge)));
        var al = this.service.shortestPath("1", "2", new PosCostMin(new ArrayList<>(List.of(new PosCostBHash("1","1","1", "2", "3", new BigDecimal("1")))), new ArrayList<>(List.of(new PosCostBHash("1", "2", "2", "2", "3", new BigDecimal("1"))))));
        assertThat(al).isNotNull();
        assertThat(al.getId()).isEqualTo("MINTCOST:1:2");

    }
}