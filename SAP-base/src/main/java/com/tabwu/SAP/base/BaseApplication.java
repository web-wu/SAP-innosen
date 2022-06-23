package com.tabwu.SAP.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ProjectName: SAP-innosen
 * @Author: tabwu
 * @Date: 2022/6/19 16:10
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.tabwu.SAP.base.mapper")
@ComponentScan("com.tabwu.SAP")
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }
}
