package com.empresa.pos.predicates;

import com.empresa.pos.dtos.response.PosCostHash;
import java.util.function.Predicate;

public class PredicateFindPosCost implements Predicate<PosCostHash> {
    private final PosCostHash posCost;

    public PredicateFindPosCost(PosCostHash posCostHash) {
        this.posCost = posCostHash;
    }

    @Override
    public boolean test(PosCostHash posCostHash) {
        boolean rightSearch = posCostHash.getIdPointA().equalsIgnoreCase(posCost.getIdPointA()) && posCostHash.getIdPointB().equalsIgnoreCase(posCost.getIdPointB());
        boolean reverseSearch = posCostHash.getIdPointA().equalsIgnoreCase(posCost.getIdPointB()) && posCostHash.getIdPointB().equalsIgnoreCase(posCost.getIdPointA());
        return (rightSearch || reverseSearch) && posCostHash.getCost().compareTo(posCost.getCost()) == 0;
    }
}
