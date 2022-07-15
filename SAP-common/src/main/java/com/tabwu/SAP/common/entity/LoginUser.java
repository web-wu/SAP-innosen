package com.tabwu.SAP.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/15 11:48
 * @DESCRIPTION:
 */
@Data
@ApiModel("LoginUser")
public class LoginUser implements Serializable {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("用户名")
    private String username;

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

    @ApiModelProperty("角色主键")
    private String rid;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("权限key")
    private String roleKey;
}
