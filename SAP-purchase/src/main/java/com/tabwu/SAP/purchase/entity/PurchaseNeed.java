package com.tabwu.SAP.purchase.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tabwu
 * @since 2022-06-23
 */
@Data
@TableName("yls_purchase_need")
@ApiModel(value = "PurchaseNeed对象", description = "")
public class PurchaseNeed implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("单据编号")
    private String code;

    @ApiModelProperty("供应商")
    private String supplier;

    @ApiModelProperty("物料条码")
    private String mcode;

    @ApiModelProperty("物料名称")
    private String name;

    @ApiModelProperty("规格")
    private String param;

    @ApiModelProperty("采购数量")
    private Integer number;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ApiModelProperty("税前总价")
    private BigDecimal allPrice;

    @ApiModelProperty("税率")
    private String tax;

    @ApiModelProperty("税额")
    private BigDecimal taxPrice;

    @ApiModelProperty("税额合计")
    private BigDecimal allTaxPrice;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("单据状态，0-未审核，1-已审核，默认0")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("跟新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    @TableLogic
    private Integer isDelete;

}
