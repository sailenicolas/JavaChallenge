package com.empresa.cache.dtos.requests;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PosCostRequest {
    private String idPointA;
    private String idPointB;
    private BigDecimal cost;

}
