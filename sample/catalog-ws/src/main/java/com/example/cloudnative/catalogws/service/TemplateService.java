package com.example.cloudnative.catalogws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.cloudnative.catalogws.dto.CatalogDto;
import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogCacheRepository;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.example.cloudnative.catalogws.util.RedisOperator;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TemplateService {

	private final RedisOperator<CatalogEntity> redisOperator;
	private final CatalogCacheRepository catalogCacheRepository;
	private final CatalogRepository catalogRepository;

	public Iterable<CatalogEntity> getAllCatalogs() {
		String key = "catalogList";
		//1. 캐쉬에서 먼저 찾는다.
		List<CatalogEntity> entities = redisOperator.getListValue(key);
		if (entities != null && entities.size() > 0) {
			//2. 캐쉬에 있으면 캐쉬에서 조회
			log.info("Cache Hit");
			return entities;
		}
		else {
			//3. 캐쉬에 없으면 DB에서 조회해서 캐쉬에 저장 후 리턴
			log.info("Cache Miss");
			List<CatalogEntity> catalogEntitiyList = new ArrayList<CatalogEntity>();
			Iterable<CatalogEntity> catalogEntities = catalogRepository.findAll();
			catalogEntities.forEach(v -> {
				catalogEntitiyList.add(new ModelMapper().map(v, CatalogEntity.class));
	        });
			redisOperator.setList(key, catalogEntitiyList, 1, TimeUnit.MINUTES);
			return catalogEntities;
		}
		
	}

	public CatalogEntity createCatalog(CatalogEntity catalogEntity) throws JsonProcessingException {
		catalogRepository.save(catalogEntity);
		CatalogDto catalogDto = new ModelMapper().map(catalogEntity, CatalogDto.class);
		log.info("Cache Save = {}", catalogDto);
		redisOperator.set(catalogEntity.getProductId(), catalogEntity, 100, TimeUnit.SECONDS);
		return catalogEntity;
	}

	public CatalogEntity getCatalog(String productId) {
		
		String key = String.format("catalog:%s", productId);
		
		//1. 캐쉬에서 먼저 찾는다.
		CatalogEntity catalogEntity = redisOperator.getValue(productId);
		
		if(catalogEntity!=null) {
			//2. 캐쉬에 있으면 캐쉬데이타 리턴
			log.info("Cache Hit = {}", key);
			return catalogEntity;
		}
		else {
			//3. 캐쉬에 없으면 DB에서 조회해서 캐쉬에 저장 후 리턴
			log.info("Cache Miss = {}", productId);
			catalogEntity = catalogRepository.findByProductId(productId);
			redisOperator.set(key, catalogEntity, 100, TimeUnit.SECONDS);
			return catalogEntity;
		}
	}

}
