package com.tabwu.SAP.seckills.controller;


import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.seckills.entity.SeckillProRelation;
import com.tabwu.SAP.seckills.entity.SeckillPromotion;
import com.tabwu.SAP.seckills.entity.SeckillSession;
import com.tabwu.SAP.seckills.entity.vo.SeckillPromotionQueryVo;
import com.tabwu.SAP.seckills.entity.vo.SeckillSessionVo;
import com.tabwu.SAP.seckills.service.ISeckillSessionService;
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
@RequestMapping("/seckills/seckill-session")
public class SeckillSessionController {
    @Autowired
    private ISeckillSessionService seckillSessionService;

    @PostMapping("/add")
    @ApiOperation(value = "添加秒杀场次")
    public R add(@ApiParam(name = "seckillSession",value = "seckillSession",required = true)
                 @RequestBody SeckillSession seckillSession) {
        seckillSessionService.save(seckillSession);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除秒杀场次")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        seckillSessionService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改秒杀场次")
    public R updateById(@ApiParam(name = "seckillSession",value = "seckillSession",required = true)
                            @RequestBody SeckillSession seckillSession) {
        seckillSessionService.updateById(seckillSession);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询秒杀场次")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        SeckillSession seckillSession = seckillSessionService.getById(id);
        return R.ok().data("seckillSession",seckillSession);
    }


    @GetMapping("/findPros/{id}")
    @ApiOperation(value = "根据场次id查询秒杀场次相关的商品信息")
    public R findSessionProsById(@ApiParam(name = "id",value = "场次id",required = true)
                         @PathVariable("id") String id) {
        List<SeckillProRelation> seckillProRelations = seckillSessionService.findSessionProsById(id);
        return R.ok().data("seckillProRelations",seckillProRelations);
    }


    @GetMapping("/list")
    @ApiOperation(value = "查询所有秒杀场次")
    public R list() {
        List<SeckillSession> list = seckillSessionService.list();
        return R.ok().data("list",list);
    }

    @PostMapping("/pageList")
    @ApiOperation(value = "查询所有秒杀场次")
    public R pageList(@ApiParam(name = "seckillSessionVo",value = "seckillSessionVo",required = true)
                      @RequestBody SeckillSessionVo seckillSessionVo) {
        HashMap<String,Object> page = seckillSessionService.pageList(seckillSessionVo);
        return R.ok().data("page",page);
    }
}
