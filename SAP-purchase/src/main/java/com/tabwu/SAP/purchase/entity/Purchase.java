package com.tabwu.SAP.purchase.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tabwu
 * @since 2022-06-29
 */
@Data
@TableName("yls_purchase")
@ApiModel(value = "Purchase对象", description = "")
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("单据编号")
    private String code;

    @ApiModelProperty("单据类型，1-需求单，2-订单，3-收货单，4-退货单，默认-1")
    private Integer type;

    @ApiModelProperty("供应商")
    private String supplier;

    @ApiModelProperty("关联单据")
    private String relationItem;

    @ApiModelProperty("交易凭据")
    private String txId;

    @ApiModelProperty("采购员")
    private String purchaser;

    @ApiModelProperty("税率")
    private String tax;

    @ApiModelProperty("税额")
    private BigDecimal taxPrice;

    @ApiModelProperty("税前总价")
    private BigDecimal allPrice;

    @ApiModelProperty("税后总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("单据状态，0-未审核，1-已审核，2-待付款，3待收货，4-已完成，5已取消，默认0")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty("跟新时间")
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    @TableLogic
    private Integer isDelete;

}
