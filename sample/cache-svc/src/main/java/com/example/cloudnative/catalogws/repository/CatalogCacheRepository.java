package com.example.cloudnative.catalogws.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.cloudnative.catalogws.dto.CatalogDto;

@Repository
public interface CatalogCacheRepository extends CrudRepository<CatalogDto, String> {
}
