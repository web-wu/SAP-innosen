package com.tabwu.SAP.statistic.service.impl;

import com.tabwu.SAP.statistic.entity.StatisticPurchase;
import com.tabwu.SAP.statistic.mapper.StatisticPurchaseMapper;
import com.tabwu.SAP.statistic.service.IStatisticPurchaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author tabwu
 * @since 2022-07-18
 */
@Service
public class StatisticPurchaseServiceImpl extends ServiceImpl<StatisticPurchaseMapper, StatisticPurchase> implements IStatisticPurchaseService {

    @Override
    public void getPutchaseReportsData(int date) {
        //TODO 远程调用采购模块的方法获取采购的数据存储到数据统计模块的数据库中
    }
}
