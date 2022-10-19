package com.example.cloudnative.catalogws.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CatalogService {

	private final CatalogRepository catalogRepository;

	public Iterable<CatalogEntity> getAllCatalogs() {
		log.info("getAllCatalogs");
		return catalogRepository.findAll();
	}

	public CatalogEntity createCatalog(CatalogEntity catalogEntity) throws JsonProcessingException {
		log.info("createCatalog = {}", catalogEntity);
		catalogRepository.save(catalogEntity);
		return catalogEntity;
	}

	@Cacheable(cacheNames = "catalog", key = "#productId")
	public CatalogEntity getCatalog(String productId) {
		log.info("getCatalog = {}", productId);
		CatalogEntity catalogEntity = catalogRepository.findByProductId(productId);
		return catalogEntity;
	}
}
