package com.empresa.data.services.impl;

import com.empresa.data.models.CreditsModel;
import com.empresa.data.repositories.DataManagerRepository;
import com.empresa.data.services.DataService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DataServiceImpl implements DataService {
    private final DataManagerRepository dataManagerRepository;
    @Override
    public Mono<CreditsModel> getById(String id) {
        return dataManagerRepository.findById(id);
    }

    @Override
    public Mono<CreditsModel> createCredits(CreditsModel id) {
        return dataManagerRepository.save(id);
    }
}
