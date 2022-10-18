package com.example.cloudnative.catalogws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogCacheRepository;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HighAvailabilityService{

	RedisTemplate<String, Object> redisTemplate;
	CatalogCacheRepository catalogCacheRepository;
	CatalogRepository catalogRepository;
	Environment env;
	CacheManager cacheManager;

	@Autowired
	public HighAvailabilityService(CatalogRepository catalogRepository, Environment env,
			RedisTemplate<String, Object> redisTemplate, CatalogCacheRepository catalogCacheRepository,
			CacheManager cacheManager) {
		this.catalogRepository = catalogRepository;
		this.catalogCacheRepository = catalogCacheRepository;
		this.env = env;
		this.redisTemplate = redisTemplate;
		this.cacheManager = cacheManager;
	}

	@Cacheable(value = "catalogs")
	public Iterable<CatalogEntity> getAllCatalogs() {
		log.info("Cache Miss");
		return catalogRepository.findAll();
	}

	
	@CacheEvict(value = "catalogs", allEntries = true)
	public CatalogEntity createCatalog(CatalogEntity catalogEntity) throws JsonProcessingException {
		log.info("createCatalog");
		catalogRepository.save(catalogEntity);
		return catalogEntity;
	}

	@Retryable(maxAttempts = 1)
	@Cacheable(value = "catalog", key = "#productId")
	public CatalogEntity getCatalog(String productId) {
		log.info("Cache Miss = {}", productId);
		CatalogEntity catalogEntity = catalogRepository.findByProductId(productId);
		return catalogEntity;
	}
	
	@Recover
	public CatalogEntity getCatalog(Exception e, String productId) {
		log.info("Fallback Cache = {}", productId);
		CatalogEntity catalogEntity = catalogRepository.findByProductId(productId);
		return catalogEntity;
	}
}
