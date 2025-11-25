package com.empresa.api.services.impl;

import com.empresa.api.dtos.requests.CreditsClientRequest;
import com.empresa.api.dtos.requests.CreditsRequest;
import com.empresa.api.dtos.response.CreditsResponse;
import com.empresa.api.services.CachePosClientService;
import com.empresa.api.services.DataClientService;
import com.empresa.api.services.DataService;
import com.empresa.core.dtos.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DataServiceImpl implements DataService {
    private final DataClientService dataClientService;
    private final CachePosClientService clientService;
    @Override
    public Mono<CreditsResponse> getById(String id) {
        return dataClientService.getById(id);
    }

    @Override
    public Mono<CreditsResponse> createCredits(CreditsRequest id) {
        return clientService.findById(id.getPointId())
                .flatMap(o -> {
                    CreditsClientRequest creditsClientRequest = new CreditsClientRequest(id);
                    creditsClientRequest.setPointName(o.getPoint());
                   return this.dataClientService.create(creditsClientRequest);
                }
        );
    }
}
