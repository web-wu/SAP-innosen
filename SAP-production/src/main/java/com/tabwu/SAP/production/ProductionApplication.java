package com.tabwu.SAP.production;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/11 15:21
 * @DESCRIPTION:
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.tabwu.SAP")
@MapperScan("com.tabwu.SAP.production.mapper")
public class ProductionApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductionApplication.class,args);
    }
}
