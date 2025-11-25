package com.empresa.cache.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("POS")
@Getter
@Setter
public class PosHash {
    private String id;
    private String point;
}
