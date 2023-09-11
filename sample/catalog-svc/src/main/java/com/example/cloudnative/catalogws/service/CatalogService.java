package com.example.cloudnative.catalogws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogCacheRepository;
import com.example.cloudnative.catalogws.repository.CatalogDto;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.example.cloudnative.catalogws.util.ThreadUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CatalogService {

	private final CatalogRepository catalogRepository;

	private final CatalogCacheRepository catalogCacheRepository;

	private final StringRedisTemplate redisTemplate;

	public Iterable<CatalogEntity> getAllCatalogs() {
		log.info("getAllCatalogs");
		List<CatalogEntity> catalogEntitys = null;
		List<CatalogDto> catalogDtos = (List<CatalogDto>) catalogCacheRepository.findAll();
		log.info("catalogDtos : {}", catalogDtos);
		boolean isEmpty = false;
		for (CatalogDto catalogDto : catalogDtos) {
			if (catalogDto == null) {
				isEmpty = true;
			} else {
				isEmpty = false;
			}
		}
		if (catalogDtos == null || catalogDtos.size() == 0 || isEmpty == true) {
			ThreadUtil.sleep();
			catalogEntitys = (List<CatalogEntity>) catalogRepository.findAll();
			List<CatalogDto> catalogs = new ArrayList<CatalogDto>();
			for (CatalogEntity catalogEntity : catalogEntitys) {
				CatalogDto catalogDto = new CatalogDto();
				catalogDto.setId(catalogEntity.getId());
				catalogDto.setProductId(catalogEntity.getProductId());
				catalogDto.setProductName(catalogEntity.getProductName());
				catalogDto.setStock(catalogEntity.getStock());
				catalogDto.setUnitPrice(catalogEntity.getUnitPrice());
				catalogs.add(catalogDto);
			}
			catalogCacheRepository.saveAll(catalogs);
			return catalogEntitys;
		} else {
			List<CatalogEntity> catalogEntitys2 = new ArrayList<CatalogEntity>();
			for (CatalogDto catalogDto : catalogDtos) {
				if (catalogDto == null) {
					continue;
				}
				CatalogEntity catalogEntity = new CatalogEntity();
				catalogEntity.setId(catalogDto.getId());
				catalogEntity.setProductId(catalogDto.getProductId());
				catalogEntity.setProductName(catalogDto.getProductName());
				catalogEntity.setStock(catalogDto.getStock());
				catalogEntity.setUnitPrice(catalogDto.getUnitPrice());
				catalogEntitys2.add(catalogEntity);
			}
			return catalogEntitys2;
		}

	}

	public CatalogEntity createCatalog(CatalogEntity catalogEntity) throws JsonProcessingException {
		log.info("createCatalog = {}", catalogEntity);
		catalogRepository.save(catalogEntity);
		return catalogEntity;
	}

	public CatalogEntity setCatalog(CatalogEntity catalogEntity) throws JsonProcessingException {
		log.info("Cache Save = {}", catalogEntity);

		ObjectMapper mapper = new ObjectMapper();
		String catalogJson = mapper.writeValueAsString(catalogEntity);
		redisTemplate.convertAndSend("Catalog", catalogJson);
		
		return catalogEntity;
	}

	@CacheEvict(value = "catalog", allEntries = true)
	public void deleteCatalog() {
		log.info("delete Cache");
	}

	public CatalogEntity getCatalog(String productId) throws JsonMappingException, JsonProcessingException {
		log.info("getCatalog = {}", productId);
		
		String key = "catalog:" + productId;
		
		ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
		
		CatalogEntity catalogEntity = null;
		
		String catalogJson = stringStringValueOperations.get(key);
		
		ObjectMapper mapper = new ObjectMapper();
		if(catalogJson==null) {
			ThreadUtil.sleep();
			catalogEntity = catalogRepository.findByProductId(productId);
			catalogJson = mapper.writeValueAsString(catalogEntity);
			stringStringValueOperations.set(key, catalogJson, 30L, TimeUnit.SECONDS); // redis set 명령어
		}
		else {
			catalogEntity = mapper.readValue(catalogJson, CatalogEntity.class);
		}
		

		return catalogEntity;
	}

	@Recover
	public CatalogEntity getCatalog(Exception e, String productId) {
		log.info("Fallback Cache = {}", productId);
		CatalogEntity catalogEntity = catalogRepository.findByProductId(productId);
		return catalogEntity;
	}

}
