package com.empresa.cache.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("POSCostMin")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PosCostMinHash {
    @Id
    private String id;
    private List<PosCostHash> points;
    private BigDecimal minTotalcost;
}
