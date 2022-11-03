package com.tabwu.SAP.attendance.controller;

import com.tabwu.SAP.attendance.service.SignIn;
import com.tabwu.SAP.common.entity.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/10/28 15:15
 * @DESCRIPTION: 人事管理
 */
@RestController
@RequestMapping("/attendance")
public class SignInController {

    @Autowired
    private SignIn signIn;

    @GetMapping("/sign/{id}")
    @ApiOperation("用户签到")
    public R signIn(@ApiParam(name = "id", value = "id", required = true)
                    @PathVariable("id") String id) {
        int i = signIn.signIn(id);
        return i > 0 ? R.ok().message("签到成功") : R.ok().message("已经签过到了，不能重复签到");
    }

    @GetMapping("/checkSign/{id}")
    @ApiOperation("获取用户签到")
    public R getSignIn(@ApiParam(name = "id", value = "id", required = true)
                       @PathVariable("id") String id) {
        int i = signIn.getSignIn(id);
        return i > 0 ? R.ok().message("已签到").data("obj", i) : R.ok().message("未签过").data("obj", i);
    }

    @GetMapping("/firstSign/{id}")
    @ApiOperation("获取用户当月首次签到日期")
    public R getFirstSign(@ApiParam(name = "id", value = "id", required = true)
                       @PathVariable("id") String id) {
        int i = signIn.getFirstSign(id);
        return R.ok().message("当月首次签到日期").data("day", i);
    }

    @PostMapping("/checkStatus/{id}")
    @ApiOperation("通过当月日期查看签到状况,只能查看当月")
    public R checkStatusByDayOfMouth(@ApiParam(name = "id", value = "id", required = true)
                      @PathVariable("id") String id, int dayOfMonth) {
        Boolean status = signIn.checkStatusByDayOfMouth(id, dayOfMonth);
        return status ? R.ok().message("已签到").data("status", status) : R.ok().message("未签到").data("status", status);
    }

    @GetMapping("/count/{id}")
    @ApiOperation("统计用户当月签到总天数")
    public R countSignIn(@ApiParam(name = "id", value = "id", required = true)
                    @PathVariable("id") String id) {
        Integer count = signIn.countSignIn(id);
        return R.ok().message("用户当月签到天数").data("count", count);
    }

    @GetMapping("/signCount/{id}")
    @ApiOperation("统计用户当月连续签到天数")
    public R signCount(@ApiParam(name = "id", value = "id", required = true)
                         @PathVariable("id") String id) {
        int count = signIn.signCount(id);
        return count > 0 ? R.ok().message("用户当月连续签到天数").data("count", count) : R.ok().message("用户当月还未有签到记录");
    }

    @PostMapping("/resign/{id}")
    @ApiOperation("补签,只能补签当月当前日期之前的")
    public R resignIn(@ApiParam(name = "id", value = "id", required = true)
                      @PathVariable("id") String id, int dayOfMonth) {
        int i = signIn.resignIn(id, dayOfMonth);
        return i > 0 ? R.ok().message("补签成功") : R.ok().message("补签失败");
    }
}
