package com.tabwu.SAP.base.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/8/3 16:50
 * @DESCRIPTION:
 */
@Data
@ApiModel(value = "LogQueryVo对象")
public class VocationQueryVo implements Serializable {
    @ApiModelProperty("当前页")
    private int current;

    @ApiModelProperty("页尺寸")
    private int size;

    @ApiModelProperty("请假类型，1-普通假，2-年假，3-产假，4-陪产假")
    private Integer type;

    @ApiModelProperty("状态，0-未审批 1-已审批")
    private Integer status;
}
