package com.tabwu.SAP.seckills.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tabwu.SAP.common.utils.PageUtil;
import com.tabwu.SAP.seckills.entity.SeckillProRelation;
import com.tabwu.SAP.seckills.entity.SeckillPromotion;
import com.tabwu.SAP.seckills.entity.SeckillSession;
import com.tabwu.SAP.seckills.entity.vo.SeckillSessionVo;
import com.tabwu.SAP.seckills.mapper.SeckillSessionMapper;
import com.tabwu.SAP.seckills.service.ISeckillProRelationService;
import com.tabwu.SAP.seckills.service.ISeckillSessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @author tabwu
 * @since 2022-07-19
 */
@Service
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionMapper, SeckillSession> implements ISeckillSessionService {

    @Autowired
    private ISeckillProRelationService seckillProRelationService;

    @Override
    public HashMap<String, Object> pageList(SeckillSessionVo seckillSessionVo) {
        Page<SeckillSession> page = new Page<>(seckillSessionVo.getCurrent(), seckillSessionVo.getSize());
        QueryWrapper<SeckillSession> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(seckillSessionVo.getName())) {
            queryWrapper.like("name",seckillSessionVo.getName());
        }

        this.page(page,queryWrapper);

        return (HashMap<String, Object>) PageUtil.queryPage(page);
    }

    @Override
    public List<SeckillProRelation> findSessionProsById(String id) {
        List<SeckillProRelation> proRelations = seckillProRelationService.list(new QueryWrapper<SeckillProRelation>().eq("promotion_session_id", id));
        return proRelations;
    }
}
