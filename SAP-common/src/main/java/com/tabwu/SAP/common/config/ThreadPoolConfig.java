package com.tabwu.SAP.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/6/23 10:05
 * @DESCRIPTION:
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public Executor executor() {
        return new ThreadPoolExecutor(5,10,
                                        10, TimeUnit.SECONDS,
                                        new LinkedBlockingDeque<>(50),
                                        Executors.defaultThreadFactory(),
                                        new ThreadPoolExecutor.AbortPolicy());
    }
}
