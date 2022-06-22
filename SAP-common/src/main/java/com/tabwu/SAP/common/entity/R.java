package com.tabwu.SAP.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: SAP-innosen
 * @Author: tabwu
 * @Date: 2022/6/11 14:52
 * @Description: 统一返回数据对象
 */
@Data
@ApiModel(value = "统一返回数据对象")
public class R {
    @ApiModelProperty("成功与否")
    private boolean success;
    @ApiModelProperty("code码")
    private int code;
    @ApiModelProperty("message说明")
    private String message;
    @ApiModelProperty("数据对象")
    private Map<String,Object> data = new HashMap<>();

    public R() {
    }

    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(20000);
        r.setMessage("成功");
        return r;
    }

    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(20004);
        r.setMessage("失败");
        return r;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R data(String key,Object obj) {
        this.data.put(key,obj);
        return this;
    }

    public R data(Map<String,Object> map) {
        this.setData(map);
        return this;
    }
}
