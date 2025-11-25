package com.empresa.cache.repositories;

import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.model.PosCostMinHash;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CachePosCostMinRepository extends CrudRepository<PosCostMinHash, String>, PagingAndSortingRepository<PosCostMinHash, String> {
}
