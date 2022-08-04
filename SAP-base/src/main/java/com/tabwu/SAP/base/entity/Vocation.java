package com.tabwu.SAP.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tabwu
 * @since 2022-08-03
 */
@Data
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

    @ApiModelProperty("一级组长审批人")
    private String approve1;

    @ApiModelProperty("二级经理审批人")
    private String approve2;

    @ApiModelProperty("三级总经理审批人")
    private String approve3;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    private Integer isDelete;

}
