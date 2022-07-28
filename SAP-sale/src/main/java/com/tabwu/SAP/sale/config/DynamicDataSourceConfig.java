package com.tabwu.SAP.sale.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.tabwu.SAP.common.constant.DbConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/28 16:55
 * @DESCRIPTION:
 */
@Configuration
public class DynamicDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource() {
        return new DruidDataSource();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    public DataSource slaveDataSource() {
        return new DruidDataSource();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource,DataSource slaveDataSource) {
        HashMap<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DbConstant.MASTER,masterDataSource);
        targetDataSource.put(DbConstant.SLAVE,slaveDataSource);
        return new DynamicDataSource(masterDataSource,targetDataSource);
    }


}
