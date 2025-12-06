package com.empresa.pos.dtos.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PosCostHash {
    private String id;
    private String idPointA;
    private String idPointB;
    private BigDecimal cost;

    public PosCostHash(String idPointA, String idPointB, BigDecimal cost) {
        this.idPointA = idPointA;
        this.idPointB = idPointB;
        this.cost = cost;
    }
}
