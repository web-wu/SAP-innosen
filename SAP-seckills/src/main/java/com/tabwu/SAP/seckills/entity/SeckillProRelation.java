package com.tabwu.SAP.seckills.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 秒杀活动商品关联
 * </p>
 *
 * @author tabwu
 * @since 2022-07-19
 */
@Data
@TableName("yls_seckill_pro_relation")
@ApiModel(value = "SeckillProRelation对象", description = "秒杀活动商品关联")
public class SeckillProRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("活动id")
    private Long promotionId;

    @ApiModelProperty("活动场次id")
    private Long promotionSessionId;

    @ApiModelProperty("商品id")
    private Long pid;

    @ApiModelProperty("秒杀价格")
    private BigDecimal seckillPrice;

    @ApiModelProperty("秒杀总量")
    private Integer seckillCount;

    @ApiModelProperty("每人限购数量")
    private Integer seckillLimit;

    @ApiModelProperty("排序")
    private Integer seckillSort;


}
