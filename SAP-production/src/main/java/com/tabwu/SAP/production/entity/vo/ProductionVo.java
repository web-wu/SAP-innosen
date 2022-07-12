package com.tabwu.SAP.production.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/12 16:45
 * @DESCRIPTION:
 */
@Data
public class ProductionVo implements Serializable {
    @ApiModelProperty("当前页")
    private int current;

    @ApiModelProperty("页尺寸")
    private int size;

    @ApiModelProperty("单据编号")
    private String code;

    @ApiModelProperty("生产类型，1-封装，2-测试，3-封测一体，默认-1")
    private Integer pType;

    @ApiModelProperty("单据类型，1-生产订单，2-生成取料单，3-生成入库单，默认-1")
    private Integer type;

    @ApiModelProperty("服务提供方公司名称")
    private String company;

    @ApiModelProperty("业务员")
    private String clerk;

    @ApiModelProperty("单据状态，0-待出料，1-已出料，2-待入库，3-已入库，4-已取消，默认0")
    private Integer status;
}
