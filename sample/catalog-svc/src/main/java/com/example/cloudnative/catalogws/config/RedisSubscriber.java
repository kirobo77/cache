package com.example.cloudnative.catalogws.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogRepository;

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
		GenericJackson2JsonRedisSerializer gs = new GenericJackson2JsonRedisSerializer();
		CatalogEntity catalogEntity = (CatalogEntity) gs.deserialize(message.getBody());
		log.info("catalogEntity =  {}", catalogEntity);
		catalogRepository.save(catalogEntity);
	}
}