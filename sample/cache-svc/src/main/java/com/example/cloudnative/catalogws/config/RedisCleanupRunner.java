package com.example.cloudnative.catalogws.config;

import java.time.Duration;
import java.util.Set;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisCleanupRunner implements ApplicationRunner {
	private final RedisTemplate<String, String> template;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		SetOperations<String, String> setOps = template.opsForSet();
		HashOperations<String, Object, Object> hashOps = template.opsForHash();
		String redisKey = "catalog";

		Set<String> ids = setOps.members(redisKey);
		for (String id : ids) {
			String key = redisKey + ":" + id;

			if (!template.hasKey(key)) {
				hashOps.put(key, "foo", "bar");
				template.expire(key, Duration.ofMillis(1));
			}
		}
	}
}