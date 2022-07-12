package com.tabwu.SAP.production.entity;

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
 * @since 2022-07-11
 */
@Data
@TableName("yls_production_item")
@ApiModel(value = "ProductionItem对象", description = "")
public class ProductionItem implements Serializable {

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

    @ApiModelProperty("规格")
    private String param;

    @ApiModelProperty("物料数量")
    private Integer number;

    @ApiModelProperty("加工费单价")
    private BigDecimal price;

    @ApiModelProperty("加工费总价")
    private BigDecimal allPrice;

    @ApiModelProperty("已收数量")
    private Integer numbered;

    @ApiModelProperty("待收数量")
    private Integer numbering;

    @ApiModelProperty("物料批号")
    private String lot;

    @ApiModelProperty("仓库id")
    private String wareId;

    @ApiModelProperty("库位id")
    private String localStorageId;

    @ApiModelProperty("单据创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("单据修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    @ApiModelProperty("物料标记，1-子物料，2-父物料")
    private Integer SubType;
}
