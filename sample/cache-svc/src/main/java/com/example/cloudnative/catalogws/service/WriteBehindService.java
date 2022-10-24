package com.example.cloudnative.catalogws.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class WriteBehindService{

	private final RedisTemplate<String, Object> redisTemplate;
	private final CatalogRepository repository;
	
	@Cacheable(value = "catalogs")
	public Iterable<CatalogEntity> getAllCatalogs() {
		log.info("Cache Miss");
		return repository.findAll();
	}

	@Cacheable(value = "catalog", key = "#catalogEntity.productId")
	public CatalogEntity setCatalog(CatalogEntity catalogEntity){
		log.info("Cache Save = {}", catalogEntity);
		redisTemplate.convertAndSend("Catalog", catalogEntity);
		return catalogEntity;
	}

	@Cacheable(value = "catalog", key = "#productId")
	public CatalogEntity getCatalog(String productId) {
		log.info("Cache Miss = {}", productId);
		CatalogEntity catalogEntity = repository.findByProductId(productId);
		return catalogEntity;
	}
	
}
