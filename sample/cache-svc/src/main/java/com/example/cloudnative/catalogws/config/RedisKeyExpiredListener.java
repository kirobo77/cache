package com.example.cloudnative.catalogws.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

	public RedisKeyExpiredListener(@Qualifier("redisContainer") RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {

		log.info("########## onMessage pattern {} | {}", new String(pattern), message.toString());
	}
}