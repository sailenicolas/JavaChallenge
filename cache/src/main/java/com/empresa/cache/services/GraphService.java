package com.empresa.cache.services;

import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.repositories.CachePosCostRepository;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BidirectionalDijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GraphService {

    private final CachePosCostRepository routeRepository;

    public Graph<String, DefaultWeightedEdge> buildBidirectionalGraph(String source, String target, List<PosCostHash> incoming, List<PosCostHash> outgoing) {

        Graph<String, DefaultWeightedEdge> graph =
                new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        graph.addVertex(source);
        graph.addVertex(target);

        Queue<String> frontierSource = new ArrayDeque<>();
        Queue<String> frontierTarget = new ArrayDeque<>();

        frontierSource.add(source);
        frontierTarget.add(target);

        Set<String> visitedSource = new HashSet<>();
        Set<String> visitedTarget = new HashSet<>();

        while (!frontierSource.isEmpty() && !frontierTarget.isEmpty()) {
        // expandir un nivel desde el origen
            String s = frontierSource.poll();
            if (visitedSource.add(s)) {
                loadEdgesFromNode(outgoing, graph, s, frontierSource);
            }

        // expandir un nivel desde el destino
            String t = frontierTarget.poll();
            if (visitedTarget.add(t)) {
                loadEdgesFromNodeReverse(incoming, graph, t, frontierTarget);
            }
            // condición de intersección
            if (!Collections.disjoint(visitedSource, visitedTarget)) {
                // ya se tocó la mitad del otro lado
                return graph;
            }
        }

        return graph;
    }

    /** Cargar edges salientes desde ORIGIN (originId = node) */
    private void loadEdgesFromNode(List<PosCostHash> outgoing,
            Graph<String, DefaultWeightedEdge> graph,
            String node,
            Queue<String> frontier) {
        for (PosCostHash e : outgoing) {
            String to = e.getIdPointB();
            if (!Objects.equals(node, to)) {
                graph.addVertex(to);
                DefaultWeightedEdge edge = graph.addEdge(node, to);
                graph.setEdgeWeight(edge, e.getCost().doubleValue());
            }
            frontier.add(to);
        }
    }

    /** Cargar edges entrantes hacia DESTINO (destId = node) */
    private void loadEdgesFromNodeReverse(List<PosCostHash> incoming,
            Graph<String, DefaultWeightedEdge> graph,
            String node,
            Queue<String> frontier) {
        for (PosCostHash e : incoming) {
            String from = e.getIdPointA();
            if (!Objects.equals(node, from)) {
                graph.addVertex(from);
                DefaultWeightedEdge edge = graph.addEdge(from, node);
                if (edge != null)
                    graph.setEdgeWeight(edge, e.getCost().doubleValue());

            }
            frontier.add(from);
        }
    }
    public GraphPath<String, DefaultWeightedEdge> shortestPath(String source, String target, List<PosCostHash> incoming, List<PosCostHash> outgoing) {
        Graph<String, DefaultWeightedEdge> graph =
                buildBidirectionalGraph(source, target, incoming, outgoing);
        BidirectionalDijkstraShortestPath<String, DefaultWeightedEdge> algo =
                new BidirectionalDijkstraShortestPath<>(graph);

        // 3. Devolver edges + weight

        return algo.getPath(source, target);
    }
}