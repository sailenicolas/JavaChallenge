package com.empresa.data.repositories;

import com.empresa.data.models.CreditsModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataManagerRepository extends ReactiveCrudRepository<CreditsModel, String> {
}
