package com.tabwu.SAP.seckills.service;

import com.tabwu.SAP.seckills.entity.SeckillProRelation;
import com.tabwu.SAP.seckills.entity.SeckillSession;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tabwu.SAP.seckills.entity.to.SeckillProductInfoTo;
import com.tabwu.SAP.seckills.entity.vo.SeckillParamsVo;
import com.tabwu.SAP.seckills.entity.vo.SeckillSessionVo;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 秒杀活动场次 服务类
 * </p>
 *
 * @author tabwu
 * @since 2022-07-19
 */
public interface ISeckillSessionService extends IService<SeckillSession> {

    HashMap<String, Object> pageList(SeckillSessionVo seckillSessionVo);

    List<SeckillProRelation> findSessionProsById(String id);

    void uploadSessionsAndRelationProduct();

    List<SeckillProductInfoTo> findSeckillProducts(Long sessionId);

    SeckillProductInfoTo getSeckillProductByPid(Long sessionId, String pid);

    String seckillPurchase(SeckillParamsVo seckillParamsVo,String uid) throws InterruptedException;
}
