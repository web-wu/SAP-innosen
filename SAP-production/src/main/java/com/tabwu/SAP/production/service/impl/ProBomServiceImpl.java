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

import java.util.ArrayList;
import java.util.List;

/**
 * @author tabwu
 * @since 2022-07-11
 */
@Service
public class ProBomServiceImpl extends ServiceImpl<ProBomMapper, ProBom> implements IProBomService {

    @Override
    public boolean addBom(BomVo bomVo) {
        try {
            ProBom bom = new ProBom();
            BeanUtils.copyProperties(bomVo,bom);
            List<BomVo.ParentMaterial> parentMaterials = bomVo.getParentMaterials();
            for (BomVo.ParentMaterial parentMaterial : parentMaterials) {
                BeanUtils.copyProperties(parentMaterial,bom);
                baseMapper.insert(bom);
            }
            return true;
        } catch (BeansException e) {
            return false;
        }
    }

    @Override
    public boolean updateBom(BomVo bomVo) {
        try {
            ProBom bom = new ProBom();
            BeanUtils.copyProperties(bomVo,bom);
            List<BomVo.ParentMaterial> parentMaterials = bomVo.getParentMaterials();
            for (BomVo.ParentMaterial parentMaterial : parentMaterials) {
                BeanUtils.copyProperties(parentMaterial,bom);
                baseMapper.updateById(bom);
            }
            return true;
        } catch (BeansException e) {
            return false;
        }
    }

    @Override
    public BomVo findOne(String id) {
        ProBom bom = baseMapper.selectById(id);
        BomVo bomVo = new BomVo();
        BeanUtils.copyProperties(bom,bomVo);
        List<ProBom> proBoms = baseMapper.selectList(new QueryWrapper<ProBom>().eq("sub_material_code", bom.getSubMaterialCode()));
        List<BomVo.ParentMaterial> parentMaterials = new ArrayList<>();
        for (ProBom proBom : proBoms) {
            BomVo.ParentMaterial parentMaterial = new BomVo.ParentMaterial();
            BeanUtils.copyProperties(proBom,parentMaterial);
            parentMaterials.add(parentMaterial);
        }
        bomVo.setParentMaterials(parentMaterials);
        return bomVo;
    }

    @Override
    public List<ProBom> findList() {
        return baseMapper.selectList(null);
    }
}
