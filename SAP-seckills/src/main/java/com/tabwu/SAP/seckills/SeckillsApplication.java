package com.tabwu.SAP.seckills;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/19 9:20
 * @DESCRIPTION:
 */
@EnableAsync
@EnableScheduling
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.tabwu.SAP")
@MapperScan("com.tabwu.SAP.seckills.mapper")
public class SeckillsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeckillsApplication.class,args);
    }
}
