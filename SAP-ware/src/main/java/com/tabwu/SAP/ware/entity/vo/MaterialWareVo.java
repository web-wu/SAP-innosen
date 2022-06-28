package com.tabwu.SAP.ware.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/6/28 14:27
 * @DESCRIPTION:
 */
@Data
@ApiModel(value = "materialWareVo对象", description = "")
public class MaterialWareVo {

    @ApiModelProperty("产品id")
    private String mid;

    @ApiModelProperty("产品类型id,1-晶圆，2-半成品，3-成品")
    @TableField(value = "m_type")
    private Integer mtype;

    @ApiModelProperty("产品批号")
    private String lot;

    @ApiModelProperty("仓库id")
    private String wid;

    @ApiModelProperty("库位id")
    private String lid;

    @ApiModelProperty("锁定库存  1--库存锁定，0--未锁定-默认")
    private Integer stockLocked;
}
