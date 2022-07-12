package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
@EnableCaching
@EnableScheduling
@Configuration
public class PictureConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @CacheEvict(cacheNames = {"picture"}, allEntries = true)
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    public void clearCache() {
        log.info("Cleaning cache");
    }
}
