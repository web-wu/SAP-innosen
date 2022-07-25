package com.tabwu.SAP.base.controller;


import com.tabwu.SAP.common.entity.Logs;
import com.tabwu.SAP.base.entity.vo.LogQueryVo;
import com.tabwu.SAP.base.service.ILogsService;
import com.tabwu.SAP.common.entity.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author tabwu
 * @since 2022-06-19
 */
@RestController
@RequestMapping("/base/logs")
public class LogsController {

    @Autowired
    private ILogsService logsService;

    @PostMapping("/add")
    @ApiOperation(value = "添加操作日志")
    public R addLog(@ApiParam(name = "logs",value = "logs",required = true)
                         @RequestBody Logs logs) {
        logsService.save(logs);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除操作日志")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        logsService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改操作日志")
    public R updateById(@ApiParam(name = "logs",value = "logs",required = true)
                            @RequestBody Logs logs) {
        logsService.updateById(logs);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询操作日志")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        Logs logs = logsService.getById(id);
        return R.ok().data("logs",logs);
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询所有操作日志")
    public R list() {
        List<Logs> list = logsService.list();
        return R.ok().data("list",list);
    }

    @PostMapping("/listPage")
    @ApiOperation(value = "分页查询操作日志")
    public R listPage(@RequestBody LogQueryVo logQueryVo) {
        Map<String, Object> pageList = logsService.listPage(logQueryVo);
        return R.ok().data("pageList",pageList);
    }
}
