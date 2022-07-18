package com.tabwu.SAP.statistic.service;

import com.tabwu.SAP.statistic.entity.StatisticPurchase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author tabwu
 * @since 2022-07-18
 */
public interface IStatisticPurchaseService extends IService<StatisticPurchase> {

    void getPutchaseReportsData(int date);
}
