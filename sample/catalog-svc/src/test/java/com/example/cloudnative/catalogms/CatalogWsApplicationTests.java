package com.example.cloudnative.catalogms;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.example.cloudnative.catalogws.repository.CatalogCacheRepository;
import com.example.cloudnative.catalogws.repository.CatalogDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class CatalogWsApplicationTests {

	@Autowired
	StringRedisTemplate redisTemplate;

	@Autowired
	private CatalogCacheRepository repostiory;

	@Test
	void test() {
		CatalogDto catalogDto = CatalogDto.builder().productId("CATALOG-006").productName("seoul").stock(10)
				.unitPrice(100).createdAt(new Date()).build();
		// 저장
		repostiory.save(catalogDto);
		// `keyspace:id` 값을 가져옴
	
		log.info("repostiory = {}",repostiory.findById(catalogDto.getProductId()));
		// CatalogDto Entity 의 @RedisHash 에 정의되어 있는 keyspace (catalog) 에 속한 키의 갯수를 구함
		log.info("repostiory.count = {}",repostiory.count());
		// 삭제
		//repostiory.delete(catalogDto);
	}
}
