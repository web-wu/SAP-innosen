package com.tabwu.SAP.purchase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/6/28 15:16
 * @DESCRIPTION:
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.tabwu.SAP")
@MapperScan("com.tabwu.SAP.purchase.mapper")
public class PurchaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(PurchaseApplication.class,args);
    }
}
