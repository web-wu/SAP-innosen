package com.tabwu.SAP.statistic.service;

import com.tabwu.SAP.statistic.entity.StatisticProduction;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author tabwu
 * @since 2022-07-18
 */
public interface IStatisticProductionService extends IService<StatisticProduction> {

    void getProductionReportsData(int date);
}
