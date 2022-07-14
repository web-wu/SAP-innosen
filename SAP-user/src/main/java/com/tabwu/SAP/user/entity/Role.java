package com.tabwu.SAP.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author tabwu
 * @since 2022-06-23
 */
@Data
@TableName("yls_role")
@ApiModel(value = "Role对象", description = "")
public class Role implements GrantedAuthority,Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("权限key")
    private String roleKey;

    @ApiModelProperty("角色描述")
    private String description;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    @TableLogic
    private Integer isDelete;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


    @Override
    @JsonIgnore
    @JSONField(serialize = false) //fastjson字符串转换时忽略 属性字段
    public String getAuthority() {
        return roleKey;
    }
}
