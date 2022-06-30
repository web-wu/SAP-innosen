package com.tabwu.SAP.purchase.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author tabwu
 * @since 2022-06-23
 */
@Data
@TableName("yls_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

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

}
