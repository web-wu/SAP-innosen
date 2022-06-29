package com.tabwu.SAP.purchase.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/6/29 14:16
 * @DESCRIPTION:
 */
@Data
@ApiModel(value = "PurchaseQueryVo对象", description = "")
public class PurchaseQueryVo {

    @ApiModelProperty("当前页")
    private Integer current;

    @ApiModelProperty("页尺寸")
    private Integer size;

    @ApiModelProperty("单据编号")
    private String code;

    @ApiModelProperty("单据类型，1-需求单，2-订单，3-收货单，4-退货单，默认1")
    private Integer type;

    @ApiModelProperty("供应商")
    private String supplier;

    @ApiModelProperty("交易凭据")
    private String txId;

    @ApiModelProperty("单据状态，0-未审核，1-已审核，2-待付款，3待收货，4-已完成，5已取消，默认0")
    private Integer status;
}
