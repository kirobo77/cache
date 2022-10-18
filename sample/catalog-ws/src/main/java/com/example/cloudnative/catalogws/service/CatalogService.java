package com.example.cloudnative.catalogws.service;

import com.example.cloudnative.catalogws.cache.CatalogCacheDto;
import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();

	CatalogCacheDto createCatalog(CatalogCacheDto catalogCacheDto) throws JsonProcessingException;

	CatalogCacheDto getCatalog(String productId);
}
