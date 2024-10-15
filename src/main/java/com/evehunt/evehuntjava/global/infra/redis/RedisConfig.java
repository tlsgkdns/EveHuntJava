package com.evehunt.evehuntjava.global.infra.redis;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    public String getHost() {
        return host;
    }

    public void setHost(@NotNull String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Bean(destroyMethod = "shutdown")
    @NotNull
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + this.getHost() + ':' + this.getPort())
                .setDnsMonitoringInterval(-1L);
        return Redisson.create(config);
    }

    @Bean
    @NotNull
    public RedisConnectionFactory redisConnectionFactory(@NotNull RedissonClient redissonClient) {
        return new RedissonConnectionFactory(redissonClient);
    }
}
