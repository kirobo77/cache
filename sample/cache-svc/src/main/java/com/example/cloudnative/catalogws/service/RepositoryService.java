package com.example.cloudnative.catalogws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.cloudnative.catalogws.dto.CatalogDto;
import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogCacheRepository;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RepositoryService {

	private final CatalogCacheRepository catalogCacheRepository;
	private final CatalogRepository catalogRepository;

	public Iterable<CatalogEntity> getAllCatalogs() {
		
		//캐쉬에서 먼저 찾는다.
		Iterable<CatalogDto> dtos = catalogCacheRepository.findAll();
		if (IterableUtils.isEmpty(dtos)==false) {
			//캐쉬에 있으면 캐쉬에서 조회
			log.info("Cache Hit");
			List<CatalogEntity> entities = new ArrayList<CatalogEntity>();
			for (CatalogDto catalogDto : dtos) {
				log.info("catalogDto = {}",catalogDto);
				if(catalogDto!=null) {
					entities.add(new ModelMapper().map(catalogDto, CatalogEntity.class));
				}
			}
			return entities;
		} else {
			//3. 캐쉬에 없으면 DB에서 조회해서 캐쉬에 저장 후 리턴
			log.info("Cache Miss");
			Iterable<CatalogEntity> catalogEntities = catalogRepository.findAll();
			for (CatalogEntity entitiy : catalogEntities) {
				catalogCacheRepository.save(new ModelMapper().map(entitiy, CatalogDto.class));
			}
			return catalogEntities;
		}
		
	}

	public CatalogEntity createCatalog(CatalogEntity catalogEntity) throws JsonProcessingException {
		catalogRepository.save(catalogEntity);
		CatalogDto catalogDto = new ModelMapper().map(catalogEntity, CatalogDto.class);
		log.info("Cache Save = {}", catalogDto);
		catalogCacheRepository.save(catalogDto);
		return catalogEntity;
	}

	public CatalogEntity getCatalog(String productId) {
		//1. 캐쉬에서 먼저 찾는다.
		Optional<CatalogDto> optional = catalogCacheRepository.findById(productId);
		
		if(optional.isPresent()) {
			//2. 캐쉬에 있으면 캐쉬데이타 리턴
			log.info("Cache Hit = {}", productId);
			return new ModelMapper().map(optional.get(), CatalogEntity.class);
		}
		else {
			//3. 캐쉬에 없으면 DB에서 조회해서 캐쉬에 저장 후 리턴
			log.info("Cache Miss = {}", productId);
			CatalogEntity catalogEntity = catalogRepository.findByProductId(productId);
			CatalogDto catalogDto = new ModelMapper().map(catalogEntity, CatalogDto.class);
			catalogCacheRepository.save(catalogDto);
			return catalogEntity;
		}
	}

}
