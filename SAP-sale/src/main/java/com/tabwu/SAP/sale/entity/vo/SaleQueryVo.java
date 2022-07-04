package com.tabwu.SAP.sale.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/4 17:14
 * @DESCRIPTION:
 */
@Data
@ApiModel("SaleQueryVo")
public class SaleQueryVo {
    @ApiModelProperty("当前页")
    private int current;

    @ApiModelProperty("页尺寸")
    private int size;

    @ApiModelProperty("单据编号")
    private String code;

    @ApiModelProperty("单据类型，1-销售订单，2-销售出库单，3-销售退货单，默认-1")
    private Integer type;

    @ApiModelProperty("客户")
    private String costomer;

    @ApiModelProperty("交易凭据")
    private String txId;

    @ApiModelProperty("销售员")
    private String saler;

    @ApiModelProperty("单据状态，0-待付款，1-已付款，2-待出货，3-已完成，4-已取消，默认0")
    private Integer status;
}
