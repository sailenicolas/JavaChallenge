package com.empresa.cache.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("PosCost")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PosCostHash {
    @Id
    private String id;

    @Indexed
    private String idPointA;

    @Indexed
    private String idPointB;
    private BigDecimal cost;
}
