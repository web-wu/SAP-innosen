package com.tabwu.SAP.production.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tabwu.SAP.production.entity.ProBom;
import com.tabwu.SAP.production.entity.vo.BomVo;
import com.tabwu.SAP.production.mapper.ProBomMapper;
import com.tabwu.SAP.production.service.IProBomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tabwu
 * @since 2022-07-11
 */
@Service
public class ProBomServiceImpl extends ServiceImpl<ProBomMapper, ProBom> implements IProBomService {

    @Override
    public BomVo findBomByCode(String code) {
        List<ProBom> proBoms = baseMapper.selectList(new QueryWrapper<ProBom>().eq("sub_material_code", code));

        BomVo bomVo = new BomVo();
        List<BomVo.ParentMaterial> parentMaterials = new ArrayList<>();
        for (ProBom proBom : proBoms) {
            BomVo.ParentMaterial parentMaterial = new BomVo.ParentMaterial();
            BeanUtils.copyProperties(proBom,parentMaterial);
            parentMaterials.add(parentMaterial);
            if (StringUtils.isEmpty(bomVo.getSubMaterialCode())) {
                BeanUtils.copyProperties(proBom,bomVo);
            }
        }
        bomVo.setParentMaterials(parentMaterials);
        return bomVo;
    }

}
