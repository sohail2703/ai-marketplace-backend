package com.aimarketplace.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;


@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public CacheManager cm(RedisConnectionFactory rf){
        RedisCacheConfiguration rcc=RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10));

        return RedisCacheManager.builder(rf)
                .cacheDefaults(rcc)
                .build();
    }

}