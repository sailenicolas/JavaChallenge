package com.empresa.cache.repositories;

import com.empresa.cache.model.PosCostHash;
import com.empresa.cache.model.PosHash;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CachePosCostRepository extends CrudRepository<PosCostHash, String>, PagingAndSortingRepository<PosCostHash, String> {
    List<PosCostHash> findAllByIdPointA(String idPointA);

    List<PosCostHash> findAllByIdPointB(String idPointB);
}
