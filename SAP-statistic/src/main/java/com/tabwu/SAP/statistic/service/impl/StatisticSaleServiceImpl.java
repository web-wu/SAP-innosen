package com.tabwu.SAP.statistic.service.impl;

import com.tabwu.SAP.statistic.entity.StatisticSale;
import com.tabwu.SAP.statistic.mapper.StatisticSaleMapper;
import com.tabwu.SAP.statistic.service.IStatisticSaleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author tabwu
 * @since 2022-07-18
 */
@Service
public class StatisticSaleServiceImpl extends ServiceImpl<StatisticSaleMapper, StatisticSale> implements IStatisticSaleService {

    @Override
    public void getSaleReportsData(int date) {
        //TODO 远程调用销售模块的方法获取销售的数据存储到数据统计模块的数据库中
    }
}
