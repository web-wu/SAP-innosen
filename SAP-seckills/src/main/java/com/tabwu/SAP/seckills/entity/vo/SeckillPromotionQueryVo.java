package com.tabwu.SAP.seckills.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/19 10:12
 * @DESCRIPTION:
 */
@Data
@ApiModel("SeckillPromotionQueryVo对象")
public class SeckillPromotionQueryVo {

    @ApiModelProperty("当前页")
    private Integer current;

    @ApiModelProperty("页尺寸")
    private Integer size;

    @ApiModelProperty("活动标题")
    private String title;
}
