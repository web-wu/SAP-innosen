package com.tabwu.SAP.statistic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author tabwu
 * @since 2022-07-18
 */
@TableName("yls_statistic_sale")
@ApiModel(value = "StatisticSale对象", description = "")
public class StatisticSale implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("统计报表开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("统计报表结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("销售订单总数")
    private Integer orderNum;

    @ApiModelProperty("已付款订单数")
    private Integer orderedNum;

    @ApiModelProperty("待付款订单数")
    private Integer orderingNum;

    @ApiModelProperty("已发货订单数")
    private Integer outputedNum;

    @ApiModelProperty("已付款还未发货订单数")
    private Integer outputingNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
    public Integer getOrderedNum() {
        return orderedNum;
    }

    public void setOrderedNum(Integer orderedNum) {
        this.orderedNum = orderedNum;
    }
    public Integer getOrderingNum() {
        return orderingNum;
    }

    public void setOrderingNum(Integer orderingNum) {
        this.orderingNum = orderingNum;
    }
    public Integer getOutputedNum() {
        return outputedNum;
    }

    public void setOutputedNum(Integer outputedNum) {
        this.outputedNum = outputedNum;
    }
    public Integer getOutputingNum() {
        return outputingNum;
    }

    public void setOutputingNum(Integer outputingNum) {
        this.outputingNum = outputingNum;
    }

    @Override
    public String toString() {
        return "StatisticSale{" +
            "id=" + id +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", orderNum=" + orderNum +
            ", orderedNum=" + orderedNum +
            ", orderingNum=" + orderingNum +
            ", outputedNum=" + outputedNum +
            ", outputingNum=" + outputingNum +
        "}";
    }
}
