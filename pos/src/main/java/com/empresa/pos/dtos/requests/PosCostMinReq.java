package com.empresa.pos.dtos.requests;

import com.empresa.pos.dtos.response.PosCostHash;
import com.empresa.pos.dtos.response.PosCostMinHash;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PosCostMinReq {
    private List<PosCostHash> points;
    private BigDecimal minTotalCost;
    private String idPointA;
    private String idPointB;
}
