package com.tabwu.SAP.seckills.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/22 16:07
 * @DESCRIPTION:
 */
@Data
@ApiModel(value = "SeckillParamsVo",description = "秒杀抢购是必须参数")
public class SeckillParamsVo implements Serializable {
    @ApiModelProperty("场次id")
    private Long sessionId;

    @ApiModelProperty("商品id")
    private String pid;

    @ApiModelProperty("秒杀数量")
    private int num;

    @ApiModelProperty("商品随机码")
    private String token;
}
