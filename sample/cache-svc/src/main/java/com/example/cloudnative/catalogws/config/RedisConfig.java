package com.example.cloudnative.catalogws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter.EnableKeyspaceEvents;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.example.cloudnative.catalogws.subscriber.RedisSubscriber;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableRedisRepositories(enableKeyspaceEvents = EnableKeyspaceEvents.ON_STARTUP)
@RequiredArgsConstructor
public class RedisConfig {

	private final CatalogRepository catalogRepository;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory("localhost",6379);
		lettuceConnectionFactory.setShareNativeConnection(false);
		return lettuceConnectionFactory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return redisTemplate;
	}

///////////////////////// PUB/SUB
	@Bean
	MessageListenerAdapter messageListenerAdapter() {
		return new MessageListenerAdapter(new RedisSubscriber(catalogRepository));
	}
	
	@Bean
	RedisMessageListenerContainer redisContainer() {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory());
		container.addMessageListener(messageListenerAdapter(), topic());
		return container;
	}

	@Bean
	ChannelTopic topic() {
		return new ChannelTopic("Catalog");
	}
}
