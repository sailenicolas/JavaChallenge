package com.empresa.data.repositories;

import com.empresa.data.models.CreditsModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataManagerRepository extends CrudRepository<CreditsModel, String> {
}
