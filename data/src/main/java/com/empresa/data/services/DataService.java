package com.empresa.data.services;

import com.empresa.data.models.CreditsModel;
import java.util.Optional;

public interface DataService {
    Optional<CreditsModel> getById(String id);

    CreditsModel createCredits(CreditsModel id);
}
