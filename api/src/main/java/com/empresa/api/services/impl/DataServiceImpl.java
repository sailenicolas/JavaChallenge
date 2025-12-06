package com.empresa.pos.services.impl;

import com.empresa.pos.dtos.requests.CreditsClientRequest;
import com.empresa.pos.dtos.requests.CreditsRequest;
import com.empresa.pos.dtos.requests.PosHashRequest;
import com.empresa.pos.dtos.response.CreditsResponse;
import com.empresa.pos.dtos.response.PosHash;
import com.empresa.core.services.CrudService;
import com.empresa.pos.services.DataClientService;
import com.empresa.pos.services.DataService;
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
    private final CrudService<PosHash, PosHashRequest> clientService;
    @Override
    public Mono<CreditsResponse> getById(String id) {
        return Mono.fromCallable(()->dataClientService.getById(id))
                .subscribeOn(SCHEDULER)
                .publishOn(SCHEDULER);
    }

    @Override
    public Mono<CreditsResponse> createCredits(CreditsRequest id) {
        Mono<PosHash> byId = clientService.getById(id.getPointId());
        return byId.flatMap(o -> {
                    CreditsClientRequest creditsClientRequest = new CreditsClientRequest(id);
                    creditsClientRequest.setPointName(o.getPoint());
                   return this.dataClientService.create(creditsClientRequest);
                }
        );
    }
}
