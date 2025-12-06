package com.empresa.cache.services;

import com.empresa.cache.dtos.requests.PostCostMinRequest;
import com.empresa.cache.dtos.response.PosCostMin;
import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.model.PosCostMinHash;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.services.CrudService;
import java.util.Optional;

public interface CrudExtraService<T, V> extends CrudService<T,V> {
    Optional<ApiResponse<PosCostHash>> getPointB(String idPointA, String idPointB);

    ApiResponse<PosCostMin> getPointsMin(String id, String idB);

    ApiResponse<PosCostMinHash> postCostMin(PostCostMinRequest postCostMinRequest);

    Optional<ApiResponse<PosCostMinHash>> getPointMinBase(String id, String idB);
}
