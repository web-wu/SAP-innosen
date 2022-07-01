package com.tabwu.SAP.purchase.entity.To;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/1 9:53
 * @DESCRIPTION:
 */
@Data
public class CostomerSupplierTo implements Serializable {

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("联系人")
    private String contact;

    @ApiModelProperty("联系电话")
    private Integer tel;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("地址")
    private String address;
}
