package com.tabwu.SAP.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/6/24 10:43
 * @DESCRIPTION:
 */
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.tabwu.SAP")
@MapperScan("com.tabwu.SAP.user.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
