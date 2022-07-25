package com.tabwu.SAP.base.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/25 15:43
 * @DESCRIPTION:
 */
@Data
@ApiModel(value = "LogQueryVo")
public class LogQueryVo implements Serializable {

    @ApiModelProperty("当前页")
    private int current;

    @ApiModelProperty("页尺寸")
    private int size;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("操作模块名称")
    private String module;

    @ApiModelProperty("操作方法")
    private String method;

    @ApiModelProperty("操作类型")
    private String operateType;
}
