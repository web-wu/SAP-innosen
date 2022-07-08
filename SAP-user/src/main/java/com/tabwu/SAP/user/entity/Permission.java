package com.tabwu.SAP.user.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tabwu
 * @since 2022-06-23
 */
@Data
@TableName("yls_permission")
@ApiModel(value = "Permission对象", description = "")
@ExcelTarget("permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("菜单名")
    @Excel(name = "菜单名")
    private String name;

    @ApiModelProperty("跳转路径url")
    @Excel(name = "跳转路径url")
    private String path;

    @ApiModelProperty("菜单类型，1==菜单 2==按钮")
    @Excel(name = "菜单类型，1==菜单 2==按钮")
    private Integer type;

    @ApiModelProperty("权限key值")
    @Excel(name = "权限key值")
    private String permissionValue;

    @ApiModelProperty("组件")
    @Excel(name = "组件")
    private String component;

    @ApiModelProperty("菜单图标")
    @Excel(name = "菜单图标")
    private String icon;

    @ApiModelProperty("层级")
    @Excel(name = "层级")
    private Integer level;

    @ApiModelProperty("菜单是否启用，0==禁用 1==启用，默认启用")
    @Excel(name = "菜单是否启用，0==禁用 1==启用，默认启用")
    private Integer status;

    @ApiModelProperty("菜单是否选择，0==禁用 1==选择")
    @Excel(name = "菜单是否选择，0==禁用 1==选择")
    private Integer selected;

    @ApiModelProperty("父菜单id，一级菜单pid默认为总菜单的id==0")
    @Excel(name = "父菜单id")
    private String pid;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    @TableLogic
    private Integer isDelete;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<Permission> children;
}
