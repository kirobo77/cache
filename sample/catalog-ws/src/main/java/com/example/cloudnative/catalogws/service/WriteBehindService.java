package com.example.cloudnative.catalogws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogCacheRepository;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WriteBehindService{

	RedisTemplate<String, Object> redisTemplate;
	CatalogCacheRepository catalogCacheRepository;
	CatalogRepository repository;
	Environment env;
	CacheManager cacheManager;

	@Autowired
	public WriteBehindService(CatalogRepository repository, Environment env,
			RedisTemplate<String, Object> redisTemplate, CatalogCacheRepository catalogCacheRepository,
			CacheManager cacheManager) {
		this.repository = repository;
		this.catalogCacheRepository = catalogCacheRepository;
		this.env = env;
		this.redisTemplate = redisTemplate;
		this.cacheManager = cacheManager;
	}

	
	@Cacheable(value = "catalogs")
	public Iterable<CatalogEntity> getAllCatalogs() {
		log.info("Cache Miss");
		return repository.findAll();
	}

	@Cacheable(value = "catalog", key = "#catalogCacheDto.productId")
	public CatalogEntity createCatalog(CatalogEntity catalogEntity) throws JsonProcessingException {
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
