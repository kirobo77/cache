package com.example.cloudnative.catalogws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.cloudnative.catalogws.cache.CatalogCacheDto;
import com.example.cloudnative.catalogws.cache.CatalogCacheRepository;
import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CatalogServiceImpl implements CatalogService {

	RedisTemplate<String, Object> redisTemplate;
	CatalogCacheRepository catalogCacheRepository;
	CatalogRepository repository;
	Environment env;

	@Autowired
	public CatalogServiceImpl(CatalogRepository repository, Environment env,
			RedisTemplate<String, Object> redisTemplate, CatalogCacheRepository catalogCacheRepository) {
		this.repository = repository;
		this.catalogCacheRepository = catalogCacheRepository;
		this.env = env;
		this.redisTemplate = redisTemplate;
	}

	@Override
	@Cacheable(value = "catalogs", cacheManager = "")
	public Iterable<CatalogEntity> getAllCatalogs() {
		log.info("getAllCatalogs");
		return repository.findAll();
	}

	@Override
	@CacheEvict(value = "catalogs", allEntries = true)
	public CatalogCacheDto createCatalog(CatalogCacheDto catalogCacheDto) throws JsonProcessingException {
		log.info("createCatalog");

		ObjectMapper mapper = new ObjectMapper();

		String data = mapper.writeValueAsString(catalogCacheDto);
		log.info("data = {}",data);

		redisTemplate.convertAndSend("Catalog", data);
		//String key = "catalog:" + catalogCacheDto.getProductId();
		//ValueOperations<String, Object> value = redisTemplate.opsForValue();
		//value.set(key, data, 10000);
		
		catalogCacheRepository.save(catalogCacheDto);
		
		return catalogCacheDto;
	}

	@Override
	public CatalogCacheDto getCatalog(String productId) {
		log.info("productId = {}",productId);
		return catalogCacheRepository.findById(productId).get();
	}
}
