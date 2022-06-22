package com.tabwu.SAP.base.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ProjectName: SAP-innosen
 * @Author: tabwu
 * @Date: 2022/6/19 15:49
 * @Description:
 */
@Data
@ApiModel(value = "CustomerSupplierVo对象", description = "")
public class CustomerSupplierVo {
    @ApiModelProperty("类型  1--客户，2---供应商，3--潜在客户，默认-1")
    private Integer type;

    @ApiModelProperty("分组id")
    private String groupId;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("联系人")
    private String contact;

    @ApiModelProperty("状态  0禁用  1启用")
    private Integer status;
}
