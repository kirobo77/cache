package com.example.cloudnative.catalogws.cache;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogCacheRepository extends CrudRepository<CatalogCacheDto, String> {
}
