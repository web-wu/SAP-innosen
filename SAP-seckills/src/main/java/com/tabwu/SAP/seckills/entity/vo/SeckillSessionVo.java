package com.tabwu.SAP.seckills.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/19 10:29
 * @DESCRIPTION:
 */
@Data
@ApiModel("SeckillSession对象")
public class SeckillSessionVo {

    @ApiModelProperty("当前页")
    private Integer current;

    @ApiModelProperty("页尺寸")
    private Integer size;

    @ApiModelProperty("场次名称")
    private String name;
}
