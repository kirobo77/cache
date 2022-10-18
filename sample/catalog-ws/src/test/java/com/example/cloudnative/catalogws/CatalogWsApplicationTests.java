package com.example.cloudnative.catalogws;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

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

	    System.out.println("result_1 = " + result_1);

	    stringStringValueOperations.increment(key); // redis incr 명령어
	    final String result_2 = stringStringValueOperations.get(key);

	    System.out.println("result_2 = " + result_2);
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

	    System.out.println("character_1 = " + character_1);

	    final Long size = stringStringListOperations.size(key);

	    System.out.println("size = " + size);

	    final List<String> ResultRange = stringStringListOperations.range(key, 0, 9);

	    System.out.println("ResultRange = " + Arrays.toString(ResultRange.toArray()));
	    
	    System.out.println("Left Pop = " + stringStringListOperations.leftPop(key));
	    
	    System.out.println("size = " + stringStringListOperations.size(key));
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

	    System.out.println("members = " + Arrays.toString(sabarada.toArray()));

	    Long size = stringStringSetOperations.size(key);

	    System.out.println("size = " + size);

	    Cursor<String> cursor = stringStringSetOperations.scan(key, ScanOptions.scanOptions().match("*").count(3).build());

	    while(cursor.hasNext()) {
	        System.out.println("cursor = " + cursor.next());
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

	    System.out.println("range = " + Arrays.toString(range.toArray()));

	    Long size = stringStringZSetOperations.size(key);

	    System.out.println("size = " + size);

	    Set<String> scoreRange = stringStringZSetOperations.rangeByScore(key, 0, 13);

	    System.out.println("scoreRange = " + Arrays.toString(scoreRange.toArray()));
	}
	
	@Test
	public void testHash() {
	    String key = "hash";

	    HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();

	    stringObjectObjectHashOperations.put(key, "Hello", "sabarada");
	    stringObjectObjectHashOperations.put(key, "Hello2", "sabarada2");
	    stringObjectObjectHashOperations.put(key, "Hello3", "sabarada3");

	    Object hello = stringObjectObjectHashOperations.get(key, "Hello");

	    System.out.println("hello = " + hello);

	    Map<Object, Object> entries = stringObjectObjectHashOperations.entries(key);

	    System.out.println("entries = " + entries.get("Hello2"));

	    Long size = stringObjectObjectHashOperations.size(key);

	    System.out.println("size = " + size);
	}
}
