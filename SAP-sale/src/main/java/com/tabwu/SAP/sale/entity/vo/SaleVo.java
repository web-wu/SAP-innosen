package com.tabwu.SAP.sale.entity.vo;

import com.tabwu.SAP.sale.entity.SaleItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/4 13:10
 * @DESCRIPTION:
 */
@Data
@ApiModel(value = "SaleVo对象")
public class SaleVo implements Serializable {
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("单据编号")
    private String code;

    @ApiModelProperty("订单号")
    private String ordersn;

    @ApiModelProperty("单据类型，1-销售订单，2-销售出库单，3-销售退货单，默认-1")
    private Integer type;

    @ApiModelProperty("客户")
    private String costomer;

    @ApiModelProperty("关联的订单")
    private String relationItem;

    @ApiModelProperty("交易凭据")
    private String txId;

    @ApiModelProperty("销售员")
    private String saler;

    @ApiModelProperty("税率，默认13%")
    private String tax;

    @ApiModelProperty("税额合计")
    private BigDecimal allTax;

    @ApiModelProperty("税前总价")
    private BigDecimal allPrice;

    @ApiModelProperty("税后总价=税前总价-税额合计")
    private BigDecimal totalPrice;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("单据状态，0-待付款，1-已付款，2-待出货，3-已完成，4-已取消，默认0")
    private Integer status;

    private List<SaleItem> saleItems;
}
