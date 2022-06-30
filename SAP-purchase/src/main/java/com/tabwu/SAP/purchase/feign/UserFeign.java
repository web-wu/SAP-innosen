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
 * @DATE: 2022/6/30 15:55
 * @DESCRIPTION:
 */
@FeignClient(value = "user-service")
public interface UserFeign {

    @GetMapping("/user/user/findUser/{username}")
    @ApiOperation(value = "根据用户名查询用户")
    R findUserByUsername(@ApiParam(name = "username",value = "username",required = true)
                                @PathVariable("username") String username);
}
