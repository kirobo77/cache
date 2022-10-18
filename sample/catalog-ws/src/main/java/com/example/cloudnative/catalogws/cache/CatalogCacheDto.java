package com.example.cloudnative.catalogws.cache;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.Data;

@Data
@RedisHash("catalog")
public class CatalogCacheDto {

	@TimeToLive
	private long expiredTime;

	@Id
	private String productId;

	private String productName;

	private Integer stock;

	private Integer unitPrice;

	private Date createdAt;
}
