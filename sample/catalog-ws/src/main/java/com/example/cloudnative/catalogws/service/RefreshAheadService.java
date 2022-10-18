package com.example.cloudnative.catalogws.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.cloudnative.catalogws.repository.CatalogCacheRepository;
import com.example.cloudnative.catalogws.repository.CatalogRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RefreshAheadService{

	RedisTemplate<String, Object> redisTemplate;
	CatalogCacheRepository catalogCacheRepository;
	CatalogRepository repository;
	Environment env;
	CacheManager cacheManager;

	@Autowired
	public RefreshAheadService(CatalogRepository repository, Environment env,
			RedisTemplate<String, Object> redisTemplate, CatalogCacheRepository catalogCacheRepository,
			CacheManager cacheManager) {
		this.repository = repository;
		this.catalogCacheRepository = catalogCacheRepository;
		this.env = env;
		this.redisTemplate = redisTemplate;
		this.cacheManager = cacheManager;
	}

	
	public void refreshAhead(String productId) {
		log.info("refreshAhead = ", productId);
		redisTemplate.expire(String.format("catalog::%s", productId), 1, TimeUnit.MINUTES);
	}



}
