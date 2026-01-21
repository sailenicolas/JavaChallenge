package com.empresa.core.dtos.responses;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PosCostResponse {
    private String id;
    private String idPointA;
    private String idPointB;
    private BigDecimal cost;

}
