package com.tabwu.SAP.production.feign;

import com.tabwu.SAP.common.entity.Logs;
import com.tabwu.SAP.common.entity.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/25 17:15
 * @DESCRIPTION:
 */
@FeignClient("base-service")
public interface LogFeignService {

    @PostMapping("/base/logs/add")
    @ApiOperation(value = "添加操作日志")
    R addLog(@ApiParam(name = "logs",value = "logs",required = true)
                    @RequestBody Logs logs);

}
