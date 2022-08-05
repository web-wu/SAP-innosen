package com.tabwu.SAP.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author tabwu
 * @since 2022-06-23
 */
@Data
@TableName("yls_user")
@ApiModel(value = "User对象", description = "")
public class User implements UserDetails,Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户昵称")
    private String neckname;

    @ApiModelProperty("职位")
    private String position;

    @ApiModelProperty("部门")
    private String department;

    @ApiModelProperty("领导id")
    private String leaderId;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("电话")
    private String tel;

    @ApiModelProperty("QQ")
    private String qq;

    @ApiModelProperty("用户状态，0==禁用 1==启用，默认启用")
    private Integer status;

    @ApiModelProperty("用户描述信息")
    private String description;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    @TableLogic
    private Integer isDelete;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<Role> roles;

    @Override
    @JsonIgnore
    @JSONField(serialize = false) //fastjson字符串转换时忽略 属性字段
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false) //fastjson字符串转换时忽略 属性字段
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false) //fastjson字符串转换时忽略 属性字段
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false) //fastjson字符串转换时忽略 属性字段
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false) //fastjson字符串转换时忽略 属性字段
    public boolean isEnabled() {
        return status == 1;
    }
}
