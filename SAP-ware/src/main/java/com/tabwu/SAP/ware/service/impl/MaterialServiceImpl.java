package com.tabwu.SAP.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tabwu.SAP.common.utils.PageUtil;
import com.tabwu.SAP.ware.entity.Material;
import com.tabwu.SAP.ware.entity.vo.MaterialVo;
import com.tabwu.SAP.ware.mapper.MaterialMapper;
import com.tabwu.SAP.ware.service.IMaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tabwu
 * @since 2022-06-11
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {

    @Override
    public Map<String, Object> queryPage(int current, int size, MaterialVo materialVo) {
        Page<Material> page = new Page<Material>(current,size);
        QueryWrapper<Material> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(materialVo.getMaterialGroupId())) {
            queryWrapper.eq("material_group_id",materialVo.getMaterialGroupId());
        }
        if (!StringUtils.isEmpty(materialVo.getBin())) {
            queryWrapper.eq("bin",materialVo.getBin());
        }
        if (!StringUtils.isEmpty(materialVo.getMfrs())) {
            queryWrapper.like("mfrs",materialVo.getMfrs());
        }
        if (!StringUtils.isEmpty(materialVo.getName())) {
            queryWrapper.like("name",materialVo.getName());
        }
        if (!StringUtils.isEmpty(materialVo.getPackaging())) {
            queryWrapper.eq("packaging",materialVo.getPackaging());
        }
        if (!StringUtils.isEmpty(materialVo.getPackageVerssion())) {
            queryWrapper.eq("package_verssion",materialVo.getPackageVerssion());
        }
        if (!StringUtils.isEmpty(materialVo.getTestVerssion())) {
            queryWrapper.eq("test_verssion",materialVo.getTestVerssion());
        }
        if (!StringUtils.isEmpty(materialVo.getTypeId())) {
            queryWrapper.eq("type_id",materialVo.getTypeId());
        }
        if (!StringUtils.isEmpty(materialVo.getCode())) {
            queryWrapper.eq("code",materialVo.getCode());
        }

        baseMapper.selectPage(page,queryWrapper);

        Map<String, Object> map = PageUtil.queryPage(page);
        return map;
    }
}
