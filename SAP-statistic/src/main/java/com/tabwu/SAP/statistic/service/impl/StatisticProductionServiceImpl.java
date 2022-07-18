package com.tabwu.SAP.statistic.service.impl;

import com.tabwu.SAP.statistic.entity.StatisticProduction;
import com.tabwu.SAP.statistic.mapper.StatisticProductionMapper;
import com.tabwu.SAP.statistic.service.IStatisticProductionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author tabwu
 * @since 2022-07-18
 */
@Service
public class StatisticProductionServiceImpl extends ServiceImpl<StatisticProductionMapper, StatisticProduction> implements IStatisticProductionService {

    @Override
    public void getProductionReportsData(int date) {
        //TODO 远程调用生产模块的方法获取生产的数据存储到数据统计模块的数据库中
    }
}
