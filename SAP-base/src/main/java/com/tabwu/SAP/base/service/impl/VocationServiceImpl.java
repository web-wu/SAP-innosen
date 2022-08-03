package com.tabwu.SAP.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tabwu.SAP.base.entity.Vocation;
import com.tabwu.SAP.base.entity.vo.VocationQueryVo;
import com.tabwu.SAP.base.mapper.VocationMapper;
import com.tabwu.SAP.base.service.IVocationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tabwu.SAP.common.utils.PageUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author tabwu
 * @since 2022-08-03
 */
@Service
public class VocationServiceImpl extends ServiceImpl<VocationMapper, Vocation> implements IVocationService {

    @Override
    public Map<String, Object> listPage(VocationQueryVo vocationQueryVo) {
        QueryWrapper<Vocation> wrapper = new QueryWrapper<>();
        Page<Vocation> page = new Page<>(vocationQueryVo.getCurrent(), vocationQueryVo.getSize());

        if (vocationQueryVo.getType() != null) {
            wrapper.eq("type",vocationQueryVo.getType());
        }

        if (vocationQueryVo.getStatus() != null) {
            wrapper.eq("status",vocationQueryVo.getStatus());
        }

        Page<Vocation> vocationPage = this.page(page, wrapper);

        return PageUtil.queryPage(vocationPage);
    }

    @Override
    public void approveVocation(String id, Integer status) {
        Vocation vocation = this.getById(id);


    }
}
