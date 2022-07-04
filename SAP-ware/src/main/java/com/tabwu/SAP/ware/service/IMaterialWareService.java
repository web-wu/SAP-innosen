package com.tabwu.SAP.ware.service;

import com.tabwu.SAP.ware.entity.MaterialWare;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tabwu.SAP.ware.entity.to.WareStockTo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tabwu
 * @since 2022-06-11
 */
public interface IMaterialWareService extends IService<MaterialWare> {

    Integer checkStockByMcode(WareStockTo wareStockTo);

    boolean reduceWareStockByCondition(List<WareStockTo> wareStockTos);
}
