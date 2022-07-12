package com.tabwu.SAP.production.service;

import com.tabwu.SAP.production.entity.Production;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tabwu.SAP.production.entity.vo.ProductionVo;

import java.util.HashMap;

/**
 * @author tabwu
 * @since 2022-07-11
 */
public interface IProductionService extends IService<Production> {

    void addProductionBills(Production production);

    void changeProductionBillsStatus(String id,Integer status);

    void changeMaterialInputBillsStatus(String id,Integer status);

    HashMap<String, Object> queryPage(ProductionVo productionVo);
}
