package com.tabwu.SAP.seckills.controller;

import com.tabwu.SAP.common.entity.LoginUser;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.common.utils.JwtUtils;
import com.tabwu.SAP.seckills.entity.to.SeckillProductInfoTo;
import com.tabwu.SAP.seckills.entity.vo.SeckillParamsVo;
import com.tabwu.SAP.seckills.service.ISeckillSessionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/22 14:35
 * @DESCRIPTION:
 */
@RestController
@RequestMapping("/seckills/seckill")
public class SeckillController {
    @Autowired
    private ISeckillSessionService seckillSessionService;


    @GetMapping("/seckillProducts/{sessionId}")
    @ApiOperation(value = "根据描述场次id查询当前场次的秒杀商品信息")
    public R seckillProducts(@ApiParam(name = "sessionId",value = "sessionId",required = true)
                                 @PathVariable("sessionId") Long sessionId) {
        List<SeckillProductInfoTo> products = seckillSessionService.findSeckillProducts(sessionId);
        return R.ok().data("products",products);
    }


    @GetMapping("/seckillProductByPid/{sessionId}/{pid}")
    @ApiOperation(value = "根据描述场次id和商品id查询商品详情信息")
    public R getSeckillProductByPid(@PathVariable("sessionId") Long sessionId,@PathVariable("pid") String pid) {
        SeckillProductInfoTo product = seckillSessionService.getSeckillProductByPid(sessionId,pid);
        return R.ok().data("product",product);
    }

    @PostMapping("/kill")
    @ApiOperation(value = "根据描述场次id和商品id以及商品随机码立即抢购")
    public R seckillPurchase(@ApiParam(name = "seckillParamsVo",value = "seckillParamsVo",required = true)
            @RequestBody SeckillParamsVo seckillParamsVo, HttpServletRequest request) throws InterruptedException {
        // 1、登录判断
        LoginUser loginUser = JwtUtils.getLoginUserByToken(request);

        if (loginUser == null) {
            throw new CostomException(20004,"用户未登录，不能参与商品抢购活动");
        }
        String orderSn = seckillSessionService.seckillPurchase(seckillParamsVo,loginUser.getId());
        return R.ok().data("orderSn",orderSn);
    }
}
