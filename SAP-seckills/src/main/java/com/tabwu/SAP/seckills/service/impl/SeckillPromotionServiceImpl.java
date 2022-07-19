package com.tabwu.SAP.seckills.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tabwu.SAP.common.utils.PageUtil;
import com.tabwu.SAP.seckills.entity.SeckillPromotion;
import com.tabwu.SAP.seckills.entity.vo.SeckillPromotionQueryVo;
import com.tabwu.SAP.seckills.mapper.SeckillPromotionMapper;
import com.tabwu.SAP.seckills.service.ISeckillPromotionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tabwu
 * @since 2022-07-19
 */
@Service
public class SeckillPromotionServiceImpl extends ServiceImpl<SeckillPromotionMapper, SeckillPromotion> implements ISeckillPromotionService {

    @Override
    public HashMap<String, Object> pageList(SeckillPromotionQueryVo seckillPromotionQueryVo) {
        Page<SeckillPromotion> page = new Page<>(seckillPromotionQueryVo.getCurrent(), seckillPromotionQueryVo.getSize());
        QueryWrapper<SeckillPromotion> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(seckillPromotionQueryVo.getTitle())) {
            queryWrapper.like("title",seckillPromotionQueryVo.getTitle());
        }

        this.page(page,queryWrapper);

        return (HashMap<String, Object>) PageUtil.queryPage(page);
    }
}
