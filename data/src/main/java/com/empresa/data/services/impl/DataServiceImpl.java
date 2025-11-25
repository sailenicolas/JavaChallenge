package com.empresa.data.services.impl;

import com.empresa.data.models.CreditsModel;
import com.empresa.data.repositories.DataManagerRepository;
import com.empresa.data.services.DataService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DataServiceImpl implements DataService {
    private final DataManagerRepository dataManagerRepository;
    @Override
    public Optional<CreditsModel> getById(String id) {
        return dataManagerRepository.findById(id);
    }

    @Override
    public CreditsModel createCredits(CreditsModel id) {
        return dataManagerRepository.save(id);
    }
}
