package com.evehunt.evehuntjava.global.infra.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.validation.constraints.NotNull;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


@EnableCaching
@Configuration
public class CacheConfig {
    @Bean
    @NotNull
    public CacheManager cacheManager() {
        CacheType[] caches = CacheType.values();
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.stream(caches).map(
                cache ->
                    new CaffeineCache(cache.getCacheName(), Caffeine.newBuilder()
                            .expireAfterWrite(cache.getExpireTime(), TimeUnit.SECONDS)
                            .maximumSize(cache.getMaxSize())
                            .build())
        ).toList());
        return cacheManager;
    }
}
