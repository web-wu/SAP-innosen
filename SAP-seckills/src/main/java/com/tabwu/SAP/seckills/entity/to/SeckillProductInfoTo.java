package com.tabwu.SAP.seckills.entity.to;

import com.tabwu.SAP.seckills.entity.Material;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/21 15:20
 * @DESCRIPTION:
 */
@Data
@ApiModel(value = "SeckillProductInfoTo",description = "秒杀商品上架redis的对象")
public class SeckillProductInfoTo {

    @ApiModelProperty("秒杀商品")
    private Material material;

    @ApiModelProperty("秒杀数量")
    private int seckillCount;

    @ApiModelProperty("秒杀价格")
    private BigDecimal seckillPrice;

    @ApiModelProperty("每人限购数量")
    private int seckillLimit;

    @ApiModelProperty("秒杀商品随机码")
    private String token;
}
