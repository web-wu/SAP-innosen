package com.tabwu.SAP.base.service;

import com.tabwu.SAP.base.entity.CustomerSupplier;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tabwu.SAP.base.entity.vo.CustomerSupplierVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tabwu
 * @since 2022-06-19
 */
public interface ICustomerSupplierService extends IService<CustomerSupplier> {

    Map<String, Object> queryPage(int current, int size, CustomerSupplierVo customerSupplierVo);
}
