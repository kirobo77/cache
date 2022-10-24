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

@Slf4j
@RequiredArgsConstructor
@Service
public class CatalogService {

	private final CatalogRepository catalogRepository;

	@Cacheable("catalog")
	public Iterable<CatalogEntity> getAllCatalogs() {
		log.info("getAllCatalogs");
		ThreadUtil.sleep();
		return catalogRepository.findAll();
	}

	public CatalogEntity createCatalog(CatalogEntity catalogEntity) throws JsonProcessingException {
		log.info("createCatalog = {}", catalogEntity);
		catalogRepository.save(catalogEntity);
		return catalogEntity;
	}


	public CatalogEntity getCatalog(String productId) {
		log.info("getCatalog = {}", productId);
		CatalogEntity catalogEntity = catalogRepository.findByProductId(productId);
		return catalogEntity;
	}
	
	@CacheEvict(value="catalog", allEntries=true)
	public void deleteCatalog() {
		log.info("delete Cache");
	}
}
