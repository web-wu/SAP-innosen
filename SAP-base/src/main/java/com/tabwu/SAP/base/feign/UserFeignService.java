package com.tabwu.SAP.base.feign;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/8/5 17:02
 * @DESCRIPTION:
 */

import com.tabwu.SAP.common.entity.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserFeignService {

    @GetMapping("/user/user/leaderId/{leaderId}")
    @ApiOperation(value = "根据id查询领导信息")
    R findLeaderByLeaderId(@PathVariable("leaderId") String leaderId);
}
