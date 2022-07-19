package com.tabwu.SAP.seckills.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 秒杀活动商品关联
 * </p>
 *
 * @author tabwu
 * @since 2022-07-19
 */
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
    private BigDecimal seckillCount;

    @ApiModelProperty("每人限购数量")
    private BigDecimal seckillLimit;

    @ApiModelProperty("排序")
    private Integer seckillSort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }
    public Long getPromotionSessionId() {
        return promotionSessionId;
    }

    public void setPromotionSessionId(Long promotionSessionId) {
        this.promotionSessionId = promotionSessionId;
    }
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
    public BigDecimal getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(BigDecimal seckillPrice) {
        this.seckillPrice = seckillPrice;
    }
    public BigDecimal getSeckillCount() {
        return seckillCount;
    }

    public void setSeckillCount(BigDecimal seckillCount) {
        this.seckillCount = seckillCount;
    }
    public BigDecimal getSeckillLimit() {
        return seckillLimit;
    }

    public void setSeckillLimit(BigDecimal seckillLimit) {
        this.seckillLimit = seckillLimit;
    }
    public Integer getSeckillSort() {
        return seckillSort;
    }

    public void setSeckillSort(Integer seckillSort) {
        this.seckillSort = seckillSort;
    }

    @Override
    public String toString() {
        return "SeckillProRelation{" +
            "id=" + id +
            ", promotionId=" + promotionId +
            ", promotionSessionId=" + promotionSessionId +
            ", pid=" + pid +
            ", seckillPrice=" + seckillPrice +
            ", seckillCount=" + seckillCount +
            ", seckillLimit=" + seckillLimit +
            ", seckillSort=" + seckillSort +
        "}";
    }
}
