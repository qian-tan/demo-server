package com.jojoreading.demo.server.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${common.redis.host}")
    private String redisHost;
    @Value("${common.redis.port}")
    private Integer redisPort;
    @Value("${common.redis.password}")
    private String redisPassword;
    @Value("${common.redis.database}")
    private Integer redisDatabase;

    @Bean
    public RedissonClient redissonClient() {
        String address = "redis://" + redisHost + ":" + redisPort;
        Config config = new Config();
        config.useSingleServer().setAddress(address)
                .setDatabase(redisDatabase)
                .setPassword(redisPassword);

        return Redisson.create(config);
    }

}
