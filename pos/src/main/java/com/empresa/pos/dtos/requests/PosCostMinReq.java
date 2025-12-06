package com.empresa.pos.dtos.requests;

import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PosCostMinReq {
    private String id;
    private List<PosCostHash> points;
    private BigDecimal minTotalCost;
    public PosCostMinReq(PosCostMinHash a) {
        this.minTotalCost = a.getMinTotalCost();
        this.points = a.getPoints();
        this.id = a.getId();
    }

    public PosCostMinReq(String cacheKey, List<PosCostHash> edges, BigDecimal bigDecimal) {
        this.id = cacheKey;
        this.points = edges;
        this.minTotalCost = bigDecimal;
    }
}
