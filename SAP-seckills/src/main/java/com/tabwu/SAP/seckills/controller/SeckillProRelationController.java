package com.tabwu.SAP.seckills.controller;


import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.seckills.entity.SeckillProRelation;
import com.tabwu.SAP.seckills.entity.SeckillSession;
import com.tabwu.SAP.seckills.service.ISeckillProRelationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tabwu
 * @since 2022-07-19
 */
@RestController
@RequestMapping("/seckills/seckill-pro-relation")
public class SeckillProRelationController {
    @Autowired
    private ISeckillProRelationService seckillProRelationService;

    @PostMapping("/add")
    @ApiOperation(value = "添加秒杀场次相关的商品关系")
    public R add(@ApiParam(name = "seckillProRelation",value = "seckillProRelation",required = true)
                 @RequestBody SeckillProRelation seckillProRelation) {
        seckillProRelationService.save(seckillProRelation);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除秒杀场次相关的商品关系")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        seckillProRelationService.removeById(id);
        return R.ok();
    }


    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询秒杀场次相关商品")
    public R findOneById(@ApiParam(name = "id",value = "商品id",required = true)
                         @PathVariable("id") String id) {
        SeckillProRelation proRelation = seckillProRelationService.getById(id);
        return R.ok().data("proRelation",proRelation);
    }
}
