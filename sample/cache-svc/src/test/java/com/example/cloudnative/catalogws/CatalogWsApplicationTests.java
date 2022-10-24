package com.example.cloudnative.catalogws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class CatalogWsApplicationTests {

	@Autowired
	StringRedisTemplate redisTemplate;

	@Test
	public void testStrings() {
		final String key = "string";

		final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

		stringStringValueOperations.set(key, "1"); // redis set 명령어
		final String result_1 = stringStringValueOperations.get(key); // redis get 명령어

		log.info("result_1 = {}", result_1);

		stringStringValueOperations.increment(key); // redis incr 명령어
		final String result_2 = stringStringValueOperations.get(key);


		log.info("result_2 = {}", result_2);
	}

	@Test
	public void testList() {
		final String key = "list";

		final ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();

		stringStringListOperations.rightPush(key, "H");
		stringStringListOperations.rightPush(key, "e");
		stringStringListOperations.rightPush(key, "l");
		stringStringListOperations.rightPush(key, "l");
		stringStringListOperations.rightPush(key, "o");

		stringStringListOperations.rightPushAll(key, " ", "s", "a", "b", "a");

		final String character_1 = stringStringListOperations.index(key, 1);

		log.info("character_1 = {}", character_1);

		final Long size = stringStringListOperations.size(key);

		log.info("size = {}", size);

		final List<String> ResultRange = stringStringListOperations.range(key, 0, 9);

		log.info("ResultRange = ", Arrays.toString(ResultRange.toArray()));

		log.info("Left Pop = {}", stringStringListOperations.leftPop(key));

		log.info("size = {}", stringStringListOperations.size(key));
	}

	@Test
	public void testSet() {
		String key = "set";
		SetOperations<String, String> stringStringSetOperations = redisTemplate.opsForSet();

		stringStringSetOperations.add(key, "H");
		stringStringSetOperations.add(key, "e");
		stringStringSetOperations.add(key, "l");
		stringStringSetOperations.add(key, "l");
		stringStringSetOperations.add(key, "o");

		Set<String> sabarada = stringStringSetOperations.members(key);

		log.info("members = ", Arrays.toString(sabarada.toArray()));

		Long size = stringStringSetOperations.size(key);

		log.info("size = {}", size);

		Cursor<String> cursor = stringStringSetOperations.scan(key,
				ScanOptions.scanOptions().match("*").count(3).build());

		while (cursor.hasNext()) {
			log.info("cursor = {}", cursor.next());
		}
	}

	@Test
	public void testSortedSet() {
		String key = "sortedSet";

		ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();

		stringStringZSetOperations.add(key, "H", 1);
		stringStringZSetOperations.add(key, "e", 5);
		stringStringZSetOperations.add(key, "l", 10);
		stringStringZSetOperations.add(key, "l", 15);
		stringStringZSetOperations.add(key, "o", 20);

		Set<String> range = stringStringZSetOperations.range(key, 0, 5);

		log.info("range = {}", Arrays.toString(range.toArray()));

		Long size = stringStringZSetOperations.size(key);

		log.info("size = {}", size);

		Set<String> scoreRange = stringStringZSetOperations.rangeByScore(key, 0, 13);

		log.info("scoreRange = {}", Arrays.toString(scoreRange.toArray()));
	}

	@Test
	public void testHash() {
		String key = "hash";

		HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();

		stringObjectObjectHashOperations.put(key, "Hello", "sabarada");
		stringObjectObjectHashOperations.put(key, "Hello2", "sabarada2");
		stringObjectObjectHashOperations.put(key, "Hello3", "sabarada3");

		Object hello = stringObjectObjectHashOperations.get(key, "Hello");

		log.info("hello = {}", hello);

		Map<Object, Object> entries = stringObjectObjectHashOperations.entries(key);

		log.info("entries = {}", entries.get("Hello2"));

		Long size = stringObjectObjectHashOperations.size(key);

		log.info("size = {}", size);
	}

	@Test
	public void testHyperLogLog() {

		HyperLogLogOperations<String, String> hyperLogLogOps = redisTemplate.opsForHyperLogLog();

		String cacheKey = "valueHyperLogLog";

		String[] arr1 = { "1", "2", "2", "3", "4", "5", "5", "5", "5", "6", "7", "7", "7" };

		hyperLogLogOps.add(cacheKey, arr1);

		log.info("count : {}", hyperLogLogOps.size(cacheKey));

	}

	@Test
	public void testGeo() {
		GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
		String[] cities = { "서울", "부산" };
		String[][] gu = { { "강남구", "서초구", "관악구", "동작구", "마포구" }, { "사하구", "해운대구", "영도구", "동래구", "수영구" } };
		Point[][] pointGu = {
				{ new Point(10, -10), new Point(11, -20), new Point(13, 10), new Point(14, 30), new Point(15, 40) },
				{ new Point(-100, 10), new Point(-110, 20), new Point(-130, 80), new Point(-140, 60),
						new Point(-150, 30) } };
		String cacheKey = "valueGeo";

		for (int x = 0; x < cities.length; x++) {
			for (int y = 0; y < 5; y++) {
				geoOps.add(cacheKey, pointGu[x][y], gu[x][y]);
			}
		}

		Distance distance = geoOps.distance(cacheKey, "강남구", "동작구");

		log.info("Distance : {}", distance.getValue());
		
		List<Point> position = geoOps.position(cacheKey, "동작구");
		
		for (Point point : position) {
			log.info("Position : {} x {}", point.getX(), point.getY());
		}
	}
}
