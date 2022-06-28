package com.tabwu.SAP.ware.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tabwu
 * @since 2022-06-11
 */
@Data
@TableName("yls_material_ware")
@ApiModel(value = "MaterialWare对象", description = "")
@ExcelTarget("materialWare")
public class MaterialWare implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("产品id")
    @Excel(name = "产品code码")
    private String mid;

    @ApiModelProperty("产品类型")
    @Excel(name = "产品类型id,1-晶圆，2-半成品，3-成品")
    @TableField(value = "m_type")
    private Integer mtype;

    @ApiModelProperty("产品批号")
    @Excel(name = "产品批号")
    private String lot;

    @ApiModelProperty("仓库id")
    @Excel(name = "仓库id")
    private String wid;

    @ApiModelProperty("库位id")
    @Excel(name = "库位id")
    private String lid;

    @ApiModelProperty("产品数量")
    @Excel(name = "产品数量")
    private Integer stock;

    @ApiModelProperty("锁定库存  1--库存锁定，0--未锁定-默认")
    @Excel(name = "锁定库存  1--库存锁定，0--未锁定-默认")
    private Integer stockLocked;

    @ApiModelProperty("创建时间 入库时间")
    @Excel(name = "入库时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @Excel(name = "修改时间")
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty("出库时间")
    @Excel(name = "出库时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime outputTime;

}
