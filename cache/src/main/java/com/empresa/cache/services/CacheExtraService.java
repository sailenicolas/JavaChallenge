package com.empresa.cache.services;

import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.model.PosCostMinHash;
import java.util.List;
import java.util.Optional;

public interface CacheExtraService<T, V> extends CacheService<T,V> {
    Optional<PosCostHash> getPointB(String idPointA, String idPointB);

    Optional<PosCostMinHash> getPointMin(String id, String idB);
}
