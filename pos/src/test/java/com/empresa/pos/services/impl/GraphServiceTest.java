package com.empresa.pos.services.impl;

import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMin;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class GraphServiceTest {
    private GraphService service;

    @BeforeEach
    void setUp() {
        this.service = new GraphService();
    }

    @Test
    void buildBidirectionalGraph() {
    }

    @Test
    void shortestPath() {

        GraphPath<String, DefaultWeightedEdge> path = this.service.shortestPath("1", "2", new PosCostMin(new ArrayList<>(List.of(new PosCostBHash("1","1","1", "2", "4", new BigDecimal("3")))), new ArrayList<>(List.of(new PosCostBHash("1","1","1", "2", "2", new BigDecimal(1))))));
    }

}