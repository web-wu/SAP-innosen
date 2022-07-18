package com.tabwu.SAP.statistic.schedule;

import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.statistic.service.IStatisticProductionService;
import com.tabwu.SAP.statistic.service.IStatisticPurchaseService;
import com.tabwu.SAP.statistic.service.IStatisticSaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Executor;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/18 15:39
 * @DESCRIPTION:
 */
@Slf4j
@Component
@EnableAsync
@EnableScheduling
public class ScheduleTask {
    @Autowired
    private Executor executor;
    @Autowired
    private IStatisticPurchaseService statisticPurchaseService;
    @Autowired
    private IStatisticSaleService statisticSaleService;
    @Autowired
    private IStatisticProductionService statisticProductionService;

    @Async
    @Scheduled(cron = "* * 3 * * ?")
    public void getPutchaseReports() {
        log.info("零晨3点远程获取前一天的各种采购单据数据");
        try {
            Date date = new Date();
            executor.execute(() -> {
                statisticPurchaseService.getPutchaseReportsData(date.getDate() -1);
            });
            executor.execute(() -> {
                statisticSaleService.getSaleReportsData(date.getDate() -1);
            });
            executor.execute(() -> {
                statisticProductionService.getProductionReportsData(date.getDate() -1);
            });
        } catch (Exception e) {
            throw new CostomException(20004,"获取前一天的采购模块的数据发生异常");
        }
    }
}
