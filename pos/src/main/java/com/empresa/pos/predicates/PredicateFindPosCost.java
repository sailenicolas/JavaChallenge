package com.empresa.pos.predicates;

import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.pos.dtos.response.PosCostHash;
import java.util.function.Predicate;

public class PredicateFindPosCost implements Predicate<PosCostBHash> {
    private final PosCostBHash posCost;

    public PredicateFindPosCost(PosCostBHash posCostHash) {
        this.posCost = posCostHash;
    }

    @Override
    public boolean test(PosCostBHash posCostHash) {
        boolean rightSearch = posCostHash.getIdPointA().equalsIgnoreCase(posCost.getIdPointA()) && posCostHash.getIdPointB().equalsIgnoreCase(posCost.getIdPointB());
        boolean reverseSearch = posCostHash.getIdPointA().equalsIgnoreCase(posCost.getIdPointB()) && posCostHash.getIdPointB().equalsIgnoreCase(posCost.getIdPointA());
        System.out.println("reverseSearch = " + reverseSearch);
        System.out.println("rightSearch = " + rightSearch);
        System.out.println("posCost = " + posCost.getCost());
        System.out.println("posCost = " + posCostHash.getCost());
        return (rightSearch || reverseSearch) && posCostHash.getCost().compareTo(posCost.getCost()) == 0;
    }
}
