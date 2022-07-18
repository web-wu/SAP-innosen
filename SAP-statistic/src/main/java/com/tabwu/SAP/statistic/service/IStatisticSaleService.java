package com.tabwu.SAP.statistic.service;

import com.tabwu.SAP.statistic.entity.StatisticSale;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author tabwu
 * @since 2022-07-18
 */
public interface IStatisticSaleService extends IService<StatisticSale> {

    void getSaleReportsData(int date);
}
