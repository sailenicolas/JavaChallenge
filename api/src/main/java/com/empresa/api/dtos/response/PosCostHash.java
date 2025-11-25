package com.empresa.api.dtos.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PosCostHash {
    private String id;
    private String idPointA;
    private String idPointB;
    private BigDecimal cost;

}
