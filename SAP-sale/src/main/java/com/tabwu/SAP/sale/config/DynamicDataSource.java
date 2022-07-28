package com.tabwu.SAP.sale.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/28 17:08
 * @DESCRIPTION:
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public DynamicDataSource(DataSource defaultDataSource, HashMap<Object,Object> targetDataSource) {
        super.setDefaultTargetDataSource(defaultDataSource); //设置默认数据源
        super.setTargetDataSources(targetDataSource); //设置数据源组
        super.afterPropertiesSet();
    }

    /**
     * 根据传入的数据库类型，在配置的数据源组的map中，判断使用的数据源类型
     * 若传入为null，则使用默认的数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return contextHolder.get();  //获取数据源类型
    }

    public static void setContextHolder(String dataSource) {
        contextHolder.set(dataSource);   //设置数据源类型
    }

    public static String getContextHolder() {
        return contextHolder.get();  //获取数据源类型
    }

    public static void removeContextHolder() {
        contextHolder.remove();  //移除数据源类型
    }
}
