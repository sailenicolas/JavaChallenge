package com.empresa.pos.services.impl;

import static com.empresa.core.utils.DataUtils.getId;

import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.core.exceptions.NotFoundServiceException;
import com.empresa.pos.dtos.response.PosCostMin;
import com.empresa.pos.dtos.response.PosCostMinHash;
import com.empresa.pos.predicates.PredicateFindPosCost;
import com.empresa.pos.services.CacheClientService;
import com.empresa.pos.services.PostCostMinService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
public class PosCostMinServiceImpl implements PostCostMinService {
    private final GraphService graphService;
    private final CacheClientService posCostService;

    @Override
    public Mono<PosCostMinHash> findById(String source, String target) {
        return posCostService.getMinCostBase(source, target)
                .filter(o -> !o.outgoing().isEmpty() || !o.incoming().isEmpty())
                .flatMap(o -> Mono.fromSupplier(() -> this.shortestPath(source, target, o))
                )
                .switchIfEmpty(Mono.error(NotFoundServiceException::new))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public PosCostMinHash shortestPath(String source, String target, PosCostMin o) {
        GraphPath<String, DefaultWeightedEdge> path = this.graphService.shortestPath(source, target, o);
        String cacheKey = getCacheKey(source, target);
        return getPosCostMinHash(o, path, cacheKey);
    }

    private PosCostMinHash getPosCostMinHash(PosCostMin posCostMin, GraphPath<String, DefaultWeightedEdge> path, String cacheKey) {
        List<PosCostBHash> edges = new ArrayList<>();
        Set<PosCostBHash> list = new HashSet<>(posCostMin.outgoing());
        list.addAll(posCostMin.incoming());
        for (DefaultWeightedEdge edge : path.getEdgeList()) {
            String idPointA = path.getGraph().getEdgeSource(edge);
            String idPointB = path.getGraph().getEdgeTarget(edge);
            BigDecimal cost = new BigDecimal("%s".formatted(path.getGraph().getEdgeWeight(edge)));
            var info = new PosCostBHash(null, idPointA, "", idPointB,"", cost);
            Predicate<PosCostBHash> posCostHashPredicate = new PredicateFindPosCost(info);
            PosCostBHash string = list
                    .parallelStream()
                    .filter(posCostHashPredicate)
                    .findFirst()
                    .orElse(info);
            edges.add(string);
        }
        return new PosCostMinHash(cacheKey, edges, new BigDecimal("%s".formatted(path.getWeight())));
    }

    private static String getCacheKey(String source, String target) {
        return getId("MINTCOST", source, target);
    }

}
