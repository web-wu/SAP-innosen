package com.tabwu.SAP.seckills.controller;


import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.seckills.entity.SeckillPromotion;
import com.tabwu.SAP.seckills.entity.vo.SeckillPromotionQueryVo;
import com.tabwu.SAP.seckills.service.ISeckillPromotionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author tabwu
 * @since 2022-07-19
 */
@RestController
@RequestMapping("/seckills/seckill-promotion")
public class SeckillPromotionController {
    @Autowired
    private ISeckillPromotionService seckillPromotionService;

    @PostMapping("/add")
    @ApiOperation(value = "添加秒杀活动")
    public R add(@ApiParam(name = "seckillPromotion",value = "seckillPromotion",required = true)
                     @RequestBody SeckillPromotion seckillPromotion) {
        seckillPromotionService.save(seckillPromotion);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除秒杀活动")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        seckillPromotionService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改秒杀活动")
    public R updateById(@ApiParam(name = "seckillPromotion",value = "seckillPromotion",required = true)
                            @RequestBody SeckillPromotion seckillPromotion) {
        seckillPromotionService.updateById(seckillPromotion);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询秒杀活动")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        SeckillPromotion seckillPromotion = seckillPromotionService.getById(id);
        return R.ok().data("seckillPromotion",seckillPromotion);
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询所有秒杀活动")
    public R list() {
        List<SeckillPromotion> list = seckillPromotionService.list();
        return R.ok().data("list",list);
    }

    @PostMapping("/pageList")
    @ApiOperation(value = "查询所有秒杀活动")
    public R pageList(@ApiParam(name = "seckillPromotionQueryVo",value = "seckillPromotionQueryVo",required = true)
                          @RequestBody SeckillPromotionQueryVo seckillPromotionQueryVo) {
        HashMap<String,Object> page = seckillPromotionService.pageList(seckillPromotionQueryVo);
        return R.ok().data("page",page);
    }
}
