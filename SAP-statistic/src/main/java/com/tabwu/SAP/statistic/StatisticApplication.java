package com.tabwu.SAP.statistic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/15 15:39
 * @DESCRIPTION:  数据报表统计
 */
@SpringBootApplication
@ComponentScan("com.tabwu.SAP")
@MapperScan("com.tabwu.SAP.mapper")
@EnableDiscoveryClient
public class StatisticApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticApplication.class,args);
    }
}
