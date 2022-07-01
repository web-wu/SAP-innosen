package com.tabwu.SAP.purchase.entity;

import com.baomidou.mybatisplus.annotation.*;

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
@TableName("yls_purchase_item")
@ApiModel(value = "PurchaseItem对象", description = "")
public class PurchaseItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("父单据编号")
    private String pcode;

    @ApiModelProperty("物料类型")
    private Integer mtype;

    @ApiModelProperty("物料条码")
    private String mcode;

    @ApiModelProperty("物料名称")
    private String name;

    @ApiModelProperty("物料批号")
    private String lot;

    @ApiModelProperty("规格")
    private String param;

    @ApiModelProperty("采购数量")
    private Integer number;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("税率")
    private String tax;

    @ApiModelProperty("税额")
    private BigDecimal allTax;

    @ApiModelProperty("税前总价")
    private BigDecimal allPrice;

    @ApiModelProperty("税后总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("已收数量")
    private Integer numbered;

    @ApiModelProperty("待收数量")
    private Integer numbering;

    @ApiModelProperty("仓库id")
    private String wareId;

    @ApiModelProperty("库位id")
    private String localStorageId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    @TableLogic
    private Integer isDelete;


}
