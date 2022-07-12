package com.tabwu.SAP.production.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tabwu
 * @since 2022-07-11
 */
@Data
@TableName("yls_production")
@ApiModel(value = "Production对象", description = "")
public class Production implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("单据编号")
    private String code;

    @ApiModelProperty("生产类型，1-封装，2-测试，3-封测一体，默认-1")
    private Integer pType;

    @ApiModelProperty("单据类型，1-生产订单，2-生成取料单，3-生成入库单，默认-1")
    private Integer type;

    @ApiModelProperty("服务提供方公司名称")
    private String company;

    @ApiModelProperty("业务员")
    private String clerk;

    @ApiModelProperty("关联的订单")
    private String relationItem;

    @ApiModelProperty("bom的id")
    private String bomId;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("单据状态，0-待出料，1-已出料，2-待入库，3-已入库，4-已取消，默认0")
    private Integer status;

    @ApiModelProperty("单据创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("单据修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("物料出库时间")
    private LocalDateTime outputTime;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private List<ProductionItem> productionItems;
}
