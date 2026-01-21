package com.empresa.pos.services.impl;

import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.core.exceptions.ServiceException;
import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMin;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.BidirectionalDijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
@Slf4j
public class GraphService {

    private Graph<String, DefaultWeightedEdge> buildBidirectionalGraph(String source, String target, PosCostMin posCostMin) {

        Graph<String, DefaultWeightedEdge> graph =
                new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        graph.addVertex(source);
        graph.addVertex(target);
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            Queue<PosCostBHash> frontierSource = new ArrayDeque<>(posCostMin.outgoing());
            while (!frontierSource.isEmpty()) {
                // expandir un nivel desde el origen
                PosCostBHash s = frontierSource.poll();
                if (s != null) {
                    boolean addVertex = graph.addVertex(s.getIdPointB());
                    log.debug("addVertex = " + addVertex + " " + s);
                    if (addVertex || graph.containsVertex(s.getIdPointB())) {
                        DefaultWeightedEdge edge = graph.addEdge(s.getIdPointA(), s.getIdPointB());
                        if (edge != null)
                            graph.setEdgeWeight(edge, s.getCost().doubleValue());
                        else
                            log.debug("edge = null {}", s);
                    } else {
                        log.debug("addVertex = {} {}", false, s);
                    }
                }

            }
        }).exceptionally((a)->{
            log.debug("Hello {} {}", ExceptionUtils.getRootCauseMessage(a), ExceptionUtils.getMessage(a));
            return null;
        });
        CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.runAsync(() -> {
            Queue<PosCostBHash> frontierTarget = new ArrayDeque<>(posCostMin.incoming());
            while (!frontierTarget.isEmpty()) {
                // expandir un nivel desde el destino
                PosCostBHash t = frontierTarget.poll();
                if (t != null) {
                    boolean addVertex = graph.addVertex(t.getIdPointA());
                    if (addVertex || graph.containsVertex(t.getIdPointA())) {
                        DefaultWeightedEdge edge = graph.addEdge(t.getIdPointB(), t.getIdPointA());
                        if (edge != null)
                            graph.setEdgeWeight(edge, t.getCost().doubleValue());
                        else
                            log.debug("edge = null {}", t);
                    } else {
                        log.debug("addVertex = " + false + " " + t);
                    }
                }
            }
        }).exceptionally((a)->{
            log.debug("Hello {} {}", ExceptionUtils.getRootCauseMessage(a), ExceptionUtils.getMessage(a));
            return null;
        });
        CompletableFuture.allOf(voidCompletableFuture, voidCompletableFuture1).join();
        return graph;
    }

    public GraphPath<String, DefaultWeightedEdge> shortestPath(String source, String target, PosCostMin posCostMin) {
        Graph<String, DefaultWeightedEdge> graph =
                buildBidirectionalGraph(source, target, posCostMin);
        if (!graph.containsVertex(source)||!graph.containsVertex(target) || !graph.getType().isWeighted()){
            throw new ServiceException("Hello", "Method", HttpStatus.BAD_REQUEST);
        }
        for (DefaultWeightedEdge weightedEdge : graph.edgeSet()) {
            System.out.println("weightedEdge1 = " + graph.getEdgeSource(weightedEdge)+" = " + graph.getEdgeTarget(weightedEdge)+" = " + graph.getEdgeWeight(weightedEdge));
        }
        if (graph.incomingEdgesOf(target).isEmpty() || graph.incomingEdgesOf(source).isEmpty()){
            throw new ServiceException("Hello2", "Method5", HttpStatus.BAD_REQUEST);
        }
        ConnectivityInspector<String, DefaultWeightedEdge> ci = new ConnectivityInspector<>(graph);
        boolean pathExists = ci.pathExists(source, target);
        if (!pathExists){
            throw new ServiceException("Hello1", "Method1", HttpStatus.BAD_REQUEST);
        }
        BidirectionalDijkstraShortestPath<String, DefaultWeightedEdge> algo =
                new BidirectionalDijkstraShortestPath<>(graph);

        GraphPath<String, DefaultWeightedEdge> path = algo.getPath(source, target);
        System.out.println("path = ");
        return path;
    }
}