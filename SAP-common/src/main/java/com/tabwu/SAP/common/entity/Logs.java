package com.tabwu.SAP.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tabwu
 * @since 2022-06-19
 */
@Data
@TableName("yls_logs")
@ApiModel(value = "Logs对象", description = "")
public class Logs implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("操作模块名称")
    private String module;

    @ApiModelProperty("操作方法")
    private String method;

    @ApiModelProperty("方法入参")
    private String inputParams;

    @ApiModelProperty("方法返回值")
    private String outputParams;

    @ApiModelProperty("操作类型")
    private String operateType;

    @ApiModelProperty("操作详情")
    private String content;

    @ApiModelProperty("客户端IP")
    private String clientIp;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    @TableLogic
    private Integer isDelete;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTtime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTtime;

}
