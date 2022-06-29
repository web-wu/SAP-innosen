package com.tabwu.SAP.purchase.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author tabwu
 * @since 2022-06-11
 */
@Data
@TableName("yls_material_ware")
@ApiModel(value = "MaterialWare对象", description = "")
public class MaterialWare implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("产品id")
    private String mid;

    @ApiModelProperty("产品类型")
    @TableField(value = "m_type")
    private Integer mtype;

    @ApiModelProperty("产品批号")
    private String lot;

    @ApiModelProperty("仓库id")
    private String wid;

    @ApiModelProperty("库位id")
    private String lid;

    @ApiModelProperty("产品数量")
    private Integer stock;

    @ApiModelProperty("锁定库存  1--库存锁定，0--未锁定-默认")
    private Integer stockLocked;

    @ApiModelProperty("创建时间 入库时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty("出库时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime outputTime;

}
