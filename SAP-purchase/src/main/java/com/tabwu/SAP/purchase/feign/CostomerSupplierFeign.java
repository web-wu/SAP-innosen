package com.tabwu.SAP.purchase.feign;

import com.tabwu.SAP.common.entity.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/6/30 15:41
 * @DESCRIPTION:
 */
@FeignClient("base-service")
public interface CostomerSupplierFeign {

    @GetMapping("/base/customer-supplier/findCompany/{company}")
    @ApiOperation(value = "根据公司名称查询客户-供应商")
    R findCostomerSupplierByCompanyName(@ApiParam(name = "company",value = "公司名称",required = true)
                               @PathVariable("company") String company);
}
