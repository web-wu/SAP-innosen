package com.tabwu.SAP.sale.entity.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/4 15:01
 * @DESCRIPTION:
 */
@Data
@ApiModel(value = "WareStockTo对象")
public class WareStockTo implements Serializable {

    @ApiModelProperty("物料条码")
    private String mcode;

    @ApiModelProperty("物料批号")
    private String lot;

    @ApiModelProperty("仓库id")
    private String wareId;

    @ApiModelProperty("库位id")
    private String localStorageId;

    @ApiModelProperty("采购数量")
    private Integer number;
}
