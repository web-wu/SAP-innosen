package com.tabwu.SAP.seckills.scheduleTask;

import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.seckills.service.ISeckillSessionService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/20 11:36
 * @DESCRIPTION:
 */
@Slf4j
@Component
public class SchedulingUploadProductToRedis {

    @Autowired
    private ISeckillSessionService seckillSessionService;
    @Autowired
    private RedissonClient redissonClient;

    private final String UPLOAD_LOCK = "seckill:upload:lock";

    @Async
    @Scheduled(cron = "*/30 * * * * ?")
    public void uploadProductToRedisOf3Days() {
        log.info("定时将活动场次与相关商品提前预上架到redis，上架3天内的活动，当前时间：{}",new Date());
        RLock lock = redissonClient.getLock(UPLOAD_LOCK);
        try {
            lock.lock();
            seckillSessionService.uploadSessionsAndRelationProduct();
        } catch (Exception e) {
            throw new CostomException(20004,"上架近3日内的秒杀场次和关联商品时发生异常");
        } finally {
            lock.unlock();
        }
    }

}
