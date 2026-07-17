package com.aimarketplace.config;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@Configuration
@EnableCaching
@EnableRedisRepositories
public class RedisConfig {


}