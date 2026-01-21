package com.empresa.cache.services;

import com.empresa.cache.dtos.requests.PostCostMinRequest;
import com.empresa.cache.dtos.response.PosCostMinBase;
import com.empresa.cache.model.PosCostMinHash;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.core.services.CrudService;
import java.util.List;
import java.util.Optional;

public interface CrudExtraService<T, V> extends CrudService<T,V, PosCostPutRequest> {
    Optional<ApiResponse<List<PosCostBHash>>> getPointA(String idPointA);

    ApiResponse<PosCostMinBase> getPointMinBase(String id, String idB);
}
