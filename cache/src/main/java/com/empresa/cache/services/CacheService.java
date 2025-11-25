package com.empresa.cache.services;

import com.empresa.cache.model.PosHash;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ProblemDetail;

public interface CacheService<T,V> {
    Optional<T> getById(String id);

    T createCache(V id);

    T delete(String id);

    T putCache(V posHash);

    List<T> getAll();
}
