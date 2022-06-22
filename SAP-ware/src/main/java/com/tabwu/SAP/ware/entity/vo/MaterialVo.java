package com.tabwu.SAP.ware.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ProjectName: SAP-innosen
 * @Author: tabwu
 * @Date: 2022/6/12 20:50
 * @Description:
 */
@Data
@ApiModel(value = "MaterialVo对象", description = "")
public class MaterialVo {

    @ApiModelProperty("物料条码")
    private String code;

    @ApiModelProperty("产品类型id")
    private String typeId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("封装")
    private String packaging;

    @ApiModelProperty("物料组id")
    private String materialGroupId;

    @ApiModelProperty("封装版本")
    private String packageVerssion;

    @ApiModelProperty("测试版本")
    private String testVerssion;

    @ApiModelProperty("BIN值")
    private String bin;

    @ApiModelProperty("制造商")
    private String mfrs;
}
