package com.example.cloudnative.catalogws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
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
public class HotKeyService{

	RedisTemplate<String, Object> redisTemplate;
	CatalogCacheRepository catalogCacheRepository;
	CatalogRepository catalogRepository;
	Environment env;
	CacheManager cacheManager;

	@Autowired
	public HotKeyService(CatalogRepository catalogRepository, Environment env,
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

	@Cacheable(value = {"catalog:1","catalog:2","catalog:3"}, key = "#productId")
	public CatalogEntity getCatalog(String productId) {
		log.info("Cache Miss = {}", productId);
		CatalogEntity catalogEntity = catalogRepository.findByProductId(productId);
		return catalogEntity;
	}

}
