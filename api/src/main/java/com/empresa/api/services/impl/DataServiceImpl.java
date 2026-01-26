package com.empresa.api.services.impl;

import com.empresa.api.dtos.requests.CreditsClientRequest;
import com.empresa.api.dtos.requests.CreditsRequest;
import com.empresa.api.dtos.response.CreditsResponse;
import com.empresa.api.dtos.response.PosHash;
import com.empresa.core.dtos.responses.ApiResponse;
import com.empresa.api.services.DataClientService;
import com.empresa.api.services.DataService;
import com.empresa.core.services.GetService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
public class DataServiceImpl implements DataService {
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newVirtualThreadPerTaskExecutor();
    public static final Scheduler SCHEDULER = Schedulers.fromExecutorService(EXECUTOR_SERVICE);
    private final DataClientService dataClientService;
    private final GetService<PosHash> clientService;
    @Override
    public Mono<CreditsResponse> getById(String id) {
        return Mono.fromCallable(()->dataClientService.getById(id))
                .subscribeOn(SCHEDULER)
                .publishOn(SCHEDULER);
    }

    @Override
    public Mono<CreditsResponse> createCredits(CreditsRequest id) {
        Mono<ApiResponse<PosHash>> byId = clientService.getById(id.getPointId());
        return byId.flatMap(o -> {
                    CreditsClientRequest creditsClientRequest = new CreditsClientRequest(id);
                    creditsClientRequest.setPointName(o.getData().getPoint());
                   return this.dataClientService.create(creditsClientRequest);
                }
        );
    }
}
