package com.example.cloudnative.catalogws.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.model.CatalogRequestModel;
import com.example.cloudnative.catalogws.model.CatalogResponseModel;
import com.example.cloudnative.catalogws.service.CacheAsideWriteAroundService;
import com.example.cloudnative.catalogws.service.RefreshAheadService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog-ms")
public class CatalogController {
	 
    private final CacheAsideWriteAroundService cacheAsideWriteAroundService;
	//private final WriteBehindService writeBehindService;
	//private final RepositoryService repositoryService;
	//private final TemplateService templateService;
    private final RefreshAheadService refreshAhead;
    
    @GetMapping("/")
    public String health() {
        return "Hi, there. I'm a Catalog microservice!";
    }

    @GetMapping(value="/catalogs")
    public ResponseEntity<List<CatalogResponseModel>> getCatalogs() {
    	log.info("getCatalogs");
        Iterable<CatalogEntity> catalogList = cacheAsideWriteAroundService.getAllCatalogs();
        List<CatalogResponseModel> result = new ArrayList<>();
        catalogList.forEach(v -> {
            result.add(new ModelMapper().map(v, CatalogResponseModel.class));
        });
        
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
    @PostMapping(value="/catalog")
    public ResponseEntity<CatalogRequestModel> setCatalog(@RequestBody CatalogRequestModel catalogRequestModel) throws JsonProcessingException {
		
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CatalogEntity catalogEntity = modelMapper.map(catalogRequestModel, CatalogEntity.class);
        catalogEntity.setCreatedAt(new Date());
        cacheAsideWriteAroundService.createCatalog(catalogEntity);

        return ResponseEntity.status(HttpStatus.OK).body(catalogRequestModel);
    }
    
    @GetMapping(value="/catalog/{productId}")
    public ResponseEntity<CatalogResponseModel> getCatalog(@PathVariable("productId") String productId) {
    	log.info("getCatalogs");
    	
    	CatalogEntity catalogEntity = cacheAsideWriteAroundService.getCatalog(productId);
        
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CatalogResponseModel catalogResponseModel = modelMapper.map(catalogEntity, CatalogResponseModel.class);
        
        return ResponseEntity.status(HttpStatus.OK).body(catalogResponseModel);
    }
    
    @GetMapping(value="/refresh-ahead/{productId}")
    public ResponseEntity<Void> refreshAhead(@PathVariable("productId") String productId)  {
    	log.info("refreshAhead = {}", productId);
    	
    	refreshAhead.refreshAhead(productId);
                
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
