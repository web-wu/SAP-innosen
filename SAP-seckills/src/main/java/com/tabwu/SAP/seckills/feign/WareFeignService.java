package com.tabwu.SAP.seckills.feign;

import com.tabwu.SAP.common.entity.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/21 16:02
 * @DESCRIPTION:
 */
@FeignClient("ware-service")
public interface WareFeignService {

    @GetMapping("/ware/material/findOne/{id}")
    @ApiOperation(value = "根据id查询物料")
    R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id);
}
