package com.example.cloudnative.catalogws.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

	private final CatalogRepository catalogRepository;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		log.info("Message received = {}", message.toString());
		ObjectMapper mapper = new ObjectMapper();
		CatalogEntity catalogEntity;
		try {
			catalogEntity = mapper.readValue(message.toString(), CatalogEntity.class);
			log.info("catalogEntity =  {}", catalogEntity);
			catalogRepository.save(catalogEntity);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}
}