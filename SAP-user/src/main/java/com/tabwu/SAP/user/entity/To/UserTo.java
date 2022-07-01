package com.tabwu.SAP.user.entity.To;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/1 9:44
 * @DESCRIPTION:
 */
@Data
public class UserTo implements Serializable {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户昵称")
    private String neckname;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("电话")
    private String tel;

    @ApiModelProperty("QQ")
    private String qq;

    @ApiModelProperty("地址")
    private String address;

}
