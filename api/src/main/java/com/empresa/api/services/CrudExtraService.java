package com.empresa.api.services;

import com.empresa.api.dtos.response.PosCostMinHash;
import com.empresa.core.dtos.requests.PosCostPutRequest;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.core.dtos.responses.PosCostBHash;
import com.empresa.core.services.CrudService;
import java.util.List;
import reactor.core.publisher.Mono;

public interface CrudExtraService<T, V> extends CrudService<T,V, PosCostPutRequest> {
    Mono<ApiResponse<List<PosCostBHash>>> getPointB(String idPointA);

    Mono<ApiResponse<PosCostMinHash>> getPointMin(String id, String idB);

    Mono<ApiResponse<T>> getById(String idA, String idB);
}
