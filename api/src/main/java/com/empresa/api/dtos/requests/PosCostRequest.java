package com.empresa.api.dtos.requests;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PosCostRequest {
    private String idPointA;
    private String idPointB;
    private BigDecimal cost;
}
