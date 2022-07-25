package com.tabwu.SAP.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/25 11:22
 * @DESCRIPTION:
 */
@Data
public class SeckillMsg {

    @ApiModelProperty("秒杀订单id")
    private String seckillOrderId;

    @ApiModelProperty("商品主键")
    private String pid;

    @ApiModelProperty("秒杀单据编号")
    private String code;

    @ApiModelProperty("秒杀数量")
    private Integer num;

    @ApiModelProperty("秒杀单价")
    private BigDecimal rice;

    @ApiModelProperty("秒杀总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("付款状态")
    private Boolean payStatus;
}
