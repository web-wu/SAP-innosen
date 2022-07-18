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
@TableName("yls_statistic_purchase")
@ApiModel(value = "StatisticPurchase对象", description = "")
public class StatisticPurchase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("统计报表开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("统计报表结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("采购订单总数")
    private Integer orderNum;

    @ApiModelProperty("已付款订单数")
    private Integer orderedNum;

    @ApiModelProperty("未付款订单数")
    private Integer orderingNum;

    @ApiModelProperty("取消的订单数")
    private Integer orderCacelNum;

    @ApiModelProperty("采购收货数量")
    private Integer inputNum;

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
    public Integer getOrderCacelNum() {
        return orderCacelNum;
    }

    public void setOrderCacelNum(Integer orderCacelNum) {
        this.orderCacelNum = orderCacelNum;
    }
    public Integer getInputNum() {
        return inputNum;
    }

    public void setInputNum(Integer inputNum) {
        this.inputNum = inputNum;
    }

    @Override
    public String toString() {
        return "StatisticPurchase{" +
            "id=" + id +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", orderNum=" + orderNum +
            ", orderedNum=" + orderedNum +
            ", orderingNum=" + orderingNum +
            ", orderCacelNum=" + orderCacelNum +
            ", inputNum=" + inputNum +
        "}";
    }
}
