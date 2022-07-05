package com.tabwu.SAP.sale;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/4 10:23
 * @DESCRIPTION:
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.tabwu.SAP")
@MapperScan("com.tabwu.SAP.sale.mapper")
@EnableFeignClients
@EnableRabbit
public class SaleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaleApplication.class,args);
    }
}
