package com.tabwu.SAP.ware.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ProjectName: SAP-innosen
 * @Author: tabwu
 * @Date: 2022/6/12 17:45
 * @Description:
 */
@Data
@ApiModel(value = "StorageLocationVo对象", description = "")
public class StorageLocationVo {
    @ApiModelProperty("仓库id")
    private String wid;
    @ApiModelProperty("库位编号")
    private String code;
}
