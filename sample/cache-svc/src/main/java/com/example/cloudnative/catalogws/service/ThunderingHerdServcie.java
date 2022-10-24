package com.example.cloudnative.catalogws.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ThunderingHerdServcie implements ApplicationListener<ApplicationReadyEvent> {

	private final CacheAsideWriteAroundService cacheAsideWriteAround;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("Cache Load Service = {}",cacheAsideWriteAround.getAllCatalogs());
	}

}
