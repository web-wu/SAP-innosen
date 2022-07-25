package com.tabwu.SAP.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


/**
 * @ProjectName: SAP-innosen
 * @Author: tabwu
 * @Date: 2022/6/11 16:30
 * @Description:
 */
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.tabwu.SAP")
@MapperScan("com.tabwu.SAP.ware.mapper")
public class WareApplication {
    public static void main(String[] args) {
        SpringApplication.run(WareApplication.class,args);
    }
}
