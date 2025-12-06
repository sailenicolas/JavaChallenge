package com.empresa.cache.dtos.requests;

import com.empresa.cache.model.PosCostHash;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCostMinRequest {
    private List<PosCostHash> points;
    private BigDecimal minTotalCost;
    private String idPointA;
    private String idPointB;
}
