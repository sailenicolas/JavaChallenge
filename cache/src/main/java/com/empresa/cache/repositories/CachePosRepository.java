package com.empresa.cache.repositories;

import com.empresa.cache.model.PosHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CachePosRepository extends CrudRepository<PosHash, String>, PagingAndSortingRepository<PosHash, String> {
}
