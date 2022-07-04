package com.tabwu.SAP.sale.service;

import com.tabwu.SAP.sale.entity.Sale;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tabwu.SAP.sale.entity.vo.SaleQueryVo;
import com.tabwu.SAP.sale.entity.vo.SaleVo;

import java.util.HashMap;

/**
 * @author tabwu
 * @since 2022-07-04
 */
public interface ISaleService extends IService<Sale> {

    boolean addSale(SaleVo saleVo);

    HashMap<String,Object> pageList(SaleQueryVo saleQueryVo);


    boolean updateSaleOrder(SaleVo saleVo);
}
