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
@TableName("yls_statistic_production")
@ApiModel(value = "StatisticProduction对象", description = "")
public class StatisticProduction implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("统计报表开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("统计报表结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("生产订单数")
    private Integer proNum;

    @ApiModelProperty("生产取料单数")
    private Integer outNum;

    @ApiModelProperty("生产入库单数")
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
    public Integer getProNum() {
        return proNum;
    }

    public void setProNum(Integer proNum) {
        this.proNum = proNum;
    }
    public Integer getOutNum() {
        return outNum;
    }

    public void setOutNum(Integer outNum) {
        this.outNum = outNum;
    }
    public Integer getInputNum() {
        return inputNum;
    }

    public void setInputNum(Integer inputNum) {
        this.inputNum = inputNum;
    }

    @Override
    public String toString() {
        return "StatisticProduction{" +
            "id=" + id +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", proNum=" + proNum +
            ", outNum=" + outNum +
            ", inputNum=" + inputNum +
        "}";
    }
}
