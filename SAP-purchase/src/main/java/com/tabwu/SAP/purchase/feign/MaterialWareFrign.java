package com.tabwu.SAP.purchase.feign;

import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.purchase.entity.MaterialWare;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/6/29 15:50
 * @DESCRIPTION:
 */
@FeignClient(value = "ware-service")
public interface MaterialWareFrign {

    @PostMapping("/ware/material-ware/materialImport")
    @ApiOperation(value = "物料入库")
    R addWare(@ApiParam(name = "materialWare",value = "materialWare",required = true)
                     @RequestBody MaterialWare materialWare);
}
