package com.tabwu.SAP.seckills.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/8 13:43
 * @DESCRIPTION: redisson分布式锁单点配置
 */
@Configuration
public class RedissonConfig {

    @Bean(destroyMethod="shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }
}
