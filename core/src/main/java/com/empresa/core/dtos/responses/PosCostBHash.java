package com.empresa.core.dtos.responses;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PosCostBHash{
    private String id;
    private String idPointA;
    private String pointNameA;
    private String idPointB;
    private String pointNameB;
    private BigDecimal cost;

}
