package com.evehunt.evehuntjava.global.infra.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    public String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ':' + String.valueOf(port))
                .setDnsMonitoringInterval(-1L);
        return Redisson.create(config);
    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedissonClient redissonClient) {
        return new RedissonConnectionFactory(redissonClient);
    }
}
