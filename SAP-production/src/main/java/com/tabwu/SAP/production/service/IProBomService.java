package com.tabwu.SAP.production.service;

import com.tabwu.SAP.production.entity.ProBom;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tabwu.SAP.production.entity.vo.BomVo;

import java.util.List;

/**
 * @author tabwu
 * @since 2022-07-11
 */
public interface IProBomService extends IService<ProBom> {

    BomVo findBomByCode(String code);

}
