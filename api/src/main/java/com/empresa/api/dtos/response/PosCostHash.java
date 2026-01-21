package com.empresa.api.dtos.response;

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

}
