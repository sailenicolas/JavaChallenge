package com.empresa.pos.dtos.response;

import com.empresa.core.dtos.responses.PosCostBHash;
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
public class PosCostMinHash {
    private String id;
    private List<PosCostBHash> points;
    private BigDecimal minTotalCost;

}
