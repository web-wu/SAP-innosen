package com.tabwu.SAP.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/5 17:13
 * @DESCRIPTION:
 */
@Data
public class MqMsg implements Serializable {

    @ApiModelProperty("付款状态")
    private Boolean payStatus;

    @ApiModelProperty("单据主键")
    private String itemId;

    @ApiModelProperty("单据编号")
    private String code;

    @ApiModelProperty("税率，默认13%")
    private String tax;

    @ApiModelProperty("税额合计")
    private BigDecimal allTax;

    @ApiModelProperty("税前总价")
    private BigDecimal allPrice;

    @ApiModelProperty("税后总价=税前总价-税额合计")
    private BigDecimal totalPrice;
}
