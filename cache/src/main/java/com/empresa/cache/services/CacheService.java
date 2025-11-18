package com.empresa.cache.services;

import com.empresa.cache.dtos.response.GetResponse;

public interface CacheService {
    GetResponse getById(String id);
}
