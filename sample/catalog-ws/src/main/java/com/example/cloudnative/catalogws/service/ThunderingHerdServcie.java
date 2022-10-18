package com.example.cloudnative.catalogws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ThunderingHerdServcie implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private CacheAsideWriteAroundService cacheAsideWriteAround;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("Cache Load Service = {}",cacheAsideWriteAround.getAllCatalogs());
	}

}
