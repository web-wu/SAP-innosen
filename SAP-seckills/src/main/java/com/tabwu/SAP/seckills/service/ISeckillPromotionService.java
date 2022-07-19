package com.tabwu.SAP.seckills.service;

import com.tabwu.SAP.seckills.entity.SeckillPromotion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tabwu.SAP.seckills.entity.vo.SeckillPromotionQueryVo;

import java.util.HashMap;

/**
 * @author tabwu
 * @since 2022-07-19
 */
public interface ISeckillPromotionService extends IService<SeckillPromotion> {

    HashMap<String, Object> pageList(SeckillPromotionQueryVo seckillPromotionQueryVo);
}
