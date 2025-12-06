package com.empresa.cache.model;

import com.empresa.cache.dtos.requests.PostCostMinRequest;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "POSCostMin", timeToLive = 200)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PosCostMinHash {
    @Id
    private String id;
    private List<PosCostHash> points;
    private BigDecimal minTotalCost;

    public PosCostMinHash(PostCostMinRequest postCostMinRequest, String id) {
        this.points = postCostMinRequest.getPoints();
        this.minTotalCost = postCostMinRequest.getMinTotalCost();
        this.id = id;
    }
}
