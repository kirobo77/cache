package com.example.cloudnative.catalogws.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.example.cloudnative.catalogws.util.ThreadUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheAsideWriteAroundService {

	private final CatalogRepository catalogRepository;

	@Cacheable(cacheNames = "catalogs", key = "'all'")
	public Iterable<CatalogEntity> getAllCatalogs() {
		log.info("Cache Miss");
		ThreadUtil.sleep();
		return catalogRepository.findAll();
	}

	@CacheEvict(cacheNames = "catalogs", allEntries = true)
	public CatalogEntity createCatalog(CatalogEntity catalogEntity) throws JsonProcessingException {
		log.info("Cache Evict catalogs = {}", catalogEntity);
		catalogRepository.save(catalogEntity);
		return catalogEntity;
	}

	@Cacheable(cacheNames = "catalog", key = "#productId")
	public CatalogEntity getCatalog(String productId) {
		log.info("Cache Miss = {}", productId);
		ThreadUtil.sleep();
		CatalogEntity catalogEntity = catalogRepository.findByProductId(productId);
		return catalogEntity;
	}

}
