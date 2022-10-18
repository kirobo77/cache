package com.example.cloudnative.catalogws.controller;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cloudnative.catalogws.cache.CatalogCacheDto;
import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.model.CatalogRequestModel;
import com.example.cloudnative.catalogws.model.CatalogResponseModel;
import com.example.cloudnative.catalogws.service.CatalogService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/catalog-ms")
public class CatalogController {
	 
    @Autowired
    CatalogService catalogService;
    
    
    @GetMapping("/")
    public String health() {
        return "Hi, there. I'm a Catalog microservice!";
    }

    @GetMapping(value="/catalogs")
    public ResponseEntity<List<CatalogResponseModel>> getCatalogs() {
    	log.info("getCatalogs");
    	
        Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();

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

        CatalogCacheDto catalogCacheDto = modelMapper.map(catalogRequestModel, CatalogCacheDto.class);
        catalogCacheDto.setCreatedAt(new Date());
        catalogService.createCatalog(catalogCacheDto);

        return ResponseEntity.status(HttpStatus.OK).body(catalogRequestModel);
    }
    
    @GetMapping(value="/catalog/{productId}")
    public ResponseEntity<CatalogResponseModel> getCatalog(@PathVariable("productId") String productId) {
    	log.info("getCatalogs");
    	
        CatalogCacheDto catalogCacheDto = catalogService.getCatalog(productId);
        
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CatalogResponseModel catalogResponseModel = modelMapper.map(catalogCacheDto, CatalogResponseModel.class);
        
        return ResponseEntity.status(HttpStatus.OK).body(catalogResponseModel);
    }
}
