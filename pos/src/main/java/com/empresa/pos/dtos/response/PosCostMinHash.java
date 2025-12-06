package com.empresa.pos.dtos.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor
public class PosCostMinHash {
    private String id;
    private List<PosCostHash> points;
    private BigDecimal minTotalCost;

}
