package com.example.cloudnative.catalogws.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RedisSubscriber implements MessageListener{
	
	@Autowired
	private CatalogRepository catalogRepository;
	
    public RedisSubscriber(CatalogRepository catalogRepository) {
		this.catalogRepository = catalogRepository;
	}

	@Override
    public void onMessage(Message message, byte[] pattern) {
      
        System.out.println("Message received: " + message.toString());
        
        ObjectMapper mapper = new ObjectMapper();
        CatalogCacheDto catalogCacheDto = null;
		try {
			catalogCacheDto = mapper.readValue(message.getBody(), CatalogCacheDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
               
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CatalogEntity catalogEntity = modelMapper.map(catalogCacheDto, CatalogEntity.class);
        
        System.out.println("catalogEntity: " + catalogEntity);
        
        catalogRepository.save(catalogEntity);
    }
}
