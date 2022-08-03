package com.tabwu.SAP.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author tabwu
 * @since 2022-08-03
 */
@TableName("yls_vocation")
@ApiModel(value = "Vocation对象", description = "")
public class Vocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("请假类型，1-普通假，2-年假，3-产假，4-陪产假")
    private Integer type;

    @ApiModelProperty("请假人id")
    private String user;

    @ApiModelProperty("请假开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("请假结束时间")
    private LocalDateTime endTtime;

    @ApiModelProperty("请假时长，单位小时，最小一小时")
    private Integer vocationTime;

    @ApiModelProperty("请假原因")
    private String cause;

    @ApiModelProperty("状态，0-未审批 1-已审批")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    private Integer isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTtime() {
        return endTtime;
    }

    public void setEndTtime(LocalDateTime endTtime) {
        this.endTtime = endTtime;
    }
    public Integer getVocationTime() {
        return vocationTime;
    }

    public void setVocationTime(Integer vocationTime) {
        this.vocationTime = vocationTime;
    }
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Vocation{" +
            "id=" + id +
            ", type=" + type +
            ", user=" + user +
            ", startTime=" + startTime +
            ", endTtime=" + endTtime +
            ", vocationTime=" + vocationTime +
            ", cause=" + cause +
            ", status=" + status +
            ", createTime=" + createTime +
            ", isDelete=" + isDelete +
        "}";
    }
}
