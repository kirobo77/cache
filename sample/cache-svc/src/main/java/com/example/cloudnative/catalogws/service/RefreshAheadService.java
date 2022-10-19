package com.example.cloudnative.catalogws.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshAheadService{

	RedisTemplate<String, Object> redisTemplate;
	
	public void refreshAhead(String productId) {
		log.info("refreshAhead = {}", productId);
		redisTemplate.expire(String.format("catalog::%s", productId), 1, TimeUnit.MINUTES);
	}

}
