package com.empresa.cache.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("POS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PosHash {
    @Id
    private String id;
    @Indexed
    private String point;
}
