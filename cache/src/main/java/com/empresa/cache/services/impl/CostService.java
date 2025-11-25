package com.empresa.cache.services.impl;

import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.model.PosCostMinHash;
import com.empresa.cache.repositories.CachePosCostMinRepository;
import com.empresa.cache.repositories.CachePosCostRepository;
import com.empresa.cache.services.GraphService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CostService {

    private final CachePosCostRepository routeRepository;
    private final CachePosCostMinRepository cachePosCostMinRepository;
    private final GraphService graphService;

    @SneakyThrows
    public Optional<PosCostMinHash> getCost(String source, String target) {
        String cacheKey = getCacheKey(source, target);
        String rKey = getCacheKey(target, source);
        List<String> cacheKey1 = List.of(cacheKey, rKey);
        Iterable<PosCostMinHash> allById = cachePosCostMinRepository.findAllById(cacheKey1);
        Optional<PosCostMinHash> cached = StreamUtils.createStreamFromIterator(allById.iterator())
                .parallel()
                .filter(Objects::nonNull)
                .findAny();
        if (cached.isPresent()) {
            return cached;
        }
        List<PosCostHash> outgoing = routeRepository.findAllByIdPointA(source);
        List<PosCostHash> incoming = routeRepository.findAllByIdPointB(target);
        GraphPath<String, DefaultWeightedEdge> path = graphService.shortestPath(source, target, incoming, outgoing);
        List<PosCostHash> edges = new ArrayList<>();
        List<PosCostHash> list = new ArrayList<>(outgoing);
        list.addAll(incoming);
        for (DefaultWeightedEdge edge : path.getEdgeList()) {
            String idPointA = path.getGraph().getEdgeSource(edge);
            String idPointB = path.getGraph().getEdgeTarget(edge);
            BigDecimal cost = new BigDecimal("%s".formatted(path.getGraph().getEdgeWeight(edge)));
            var info = new PosCostHash();
            info.setIdPointA(idPointA);
            info.setIdPointB(idPointB);
            info.setCost(cost);
            PosCostHash string = list.parallelStream()
                    .filter(o -> (o.getIdPointA().equalsIgnoreCase(idPointA) && o.getIdPointB().equalsIgnoreCase(idPointB) || o.getIdPointA().equalsIgnoreCase(idPointB) && o.getIdPointB().equalsIgnoreCase(idPointA)) && o.getCost().compareTo(cost) == 0)
                    .findFirst()
                    .orElse(info);
            edges.add(string);
        }
        PosCostMinHash posCostMinHash = new PosCostMinHash(cacheKey, edges, new BigDecimal("%s".formatted(path.getWeight())));
        PosCostMinHash rPosCostMinHash = new PosCostMinHash(rKey, edges, new BigDecimal("%s".formatted(path.getWeight())));
        Iterable<PosCostMinHash> posCostMinHashes = cachePosCostMinRepository.saveAll(List.of(posCostMinHash, rPosCostMinHash));

        return StreamUtils.createStreamFromIterator(posCostMinHashes.iterator()).toList().stream().findFirst();
    }

    private static String getCacheKey(String source, String target) {
        return "MINTCOST:" + source + ":" + target;
    }
}
