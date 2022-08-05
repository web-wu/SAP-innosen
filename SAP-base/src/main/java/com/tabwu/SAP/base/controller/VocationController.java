package com.tabwu.SAP.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tabwu.SAP.base.entity.Position;
import com.tabwu.SAP.base.entity.Vocation;
import com.tabwu.SAP.base.entity.vo.VocationQueryVo;
import com.tabwu.SAP.base.service.IVocationService;
import com.tabwu.SAP.common.entity.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author tabwu
 * @since 2022-08-03
 * 请假处理
 */
@RestController
@RequestMapping("/base/vocation")
public class VocationController {
    @Autowired
    private IVocationService vocationService;

    @PostMapping("/add")
    @ApiOperation(value = "添加请假条")
    public R add(@ApiParam(name = "vocation",value = "vocation",required = true)
                         @RequestBody Vocation vocation) {
        vocationService.save(vocation);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除请假条")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        vocationService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改请假条")
    public R updateById(@ApiParam(name = "vocation",value = "vocation",required = true)
                            @RequestBody Vocation vocation) {
        vocationService.updateById(vocation);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询请假条")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        Vocation vocation = vocationService.getById(id);
        return R.ok().data("vocation",vocation);
    }

    @GetMapping("/findVocation/{uid}")
    @ApiOperation(value = "根据用户id查询请假条")
    public R findVocationByUid(@ApiParam(name = "uid",value = "uid",required = true)
                         @PathVariable("uid") String uid) {
        List<Vocation> vocations = vocationService.list(new QueryWrapper<Vocation>().eq("user", uid));
        return R.ok().data("vocations",vocations);
    }


    @PostMapping("/page")
    @ApiOperation(value = "分页查询所有请假条")
    public R listPage(@RequestBody VocationQueryVo vocationQueryVo) {
        Map<String,Object> map = vocationService.listPage(vocationQueryVo);
        return R.ok().data("map",map);
    }


    @GetMapping("/list")
    @ApiOperation(value = "查询所有请假条")
    public R list() {
        List<Vocation> list = vocationService.list();
        return R.ok().data("list",list);
    }


    @GetMapping("/approve/{id}")
    @ApiOperation(value = "根据id修改请假条状态")
    public R approveVocation(HttpServletRequest request, @PathVariable("id") String id) {
        vocationService.approveVocation(id,request);
        return R.ok();
    }
}
