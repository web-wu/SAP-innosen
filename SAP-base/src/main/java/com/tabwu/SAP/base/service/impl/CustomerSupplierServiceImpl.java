package com.tabwu.SAP.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tabwu.SAP.base.entity.CustomerSupplier;
import com.tabwu.SAP.base.entity.vo.CustomerSupplierVo;
import com.tabwu.SAP.base.mapper.CustomerSupplierMapper;
import com.tabwu.SAP.base.service.ICustomerSupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tabwu.SAP.common.utils.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tabwu
 * @since 2022-06-19
 */
@Service
public class CustomerSupplierServiceImpl extends ServiceImpl<CustomerSupplierMapper, CustomerSupplier> implements ICustomerSupplierService {

    @Override
    public Map<String, Object> queryPage(int current, int size, CustomerSupplierVo customerSupplierVo) {
        Page<CustomerSupplier> page = new Page<>(current, size);
        QueryWrapper<CustomerSupplier> wrapper = new QueryWrapper<>();

        if (customerSupplierVo.getType() > 0) {
            wrapper.eq("type",customerSupplierVo.getType());
        }
        if (!StringUtils.isEmpty(customerSupplierVo.getGroupId())) {
            wrapper.eq("group_id",customerSupplierVo.getGroupId());
        }

        if (!StringUtils.isEmpty(customerSupplierVo.getCompanyName())) {
            wrapper.like("company_name",customerSupplierVo.getCompanyName());
        }

        if (!StringUtils.isEmpty(customerSupplierVo.getContact())) {
            wrapper.eq("contact",customerSupplierVo.getContact());
        }

        if (customerSupplierVo.getStatus() > 0) {
            wrapper.eq("status",customerSupplierVo.getStatus());
        }

        baseMapper.selectPage(page,wrapper);
        Map<String, Object> map = PageUtil.queryPage(page);
        return map;
    }
}
