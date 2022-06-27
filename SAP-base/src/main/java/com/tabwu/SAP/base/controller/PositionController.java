package com.tabwu.SAP.base.controller;


import com.tabwu.SAP.base.entity.Position;
import com.tabwu.SAP.base.service.IPositionService;
import com.tabwu.SAP.common.entity.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tabwu
 * @since 2022-06-23
 */
@RestController
@RequestMapping("/base/position")
public class PositionController {
    @Autowired
    private IPositionService positionService;

    @PostMapping("/add")
    @ApiOperation(value = "添加职位")
    public R addPosition(@ApiParam(name = "position",value = "position",required = true)
                           @RequestBody Position position) {
        positionService.save(position);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除职位")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        positionService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改职位")
    public R updateById(@ApiParam(name = "position",value = "position",required = true)
                            @RequestBody Position position) {
        positionService.updateById(position);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询职位")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        Position position = positionService.getById(id);
        return R.ok().data("position",position);
    }

    @GetMapping("list")
    @ApiOperation(value = "查询所有职位")
    public R list() {
        List<Position> list = positionService.list();
        return R.ok().data("list",list);
    }
}
