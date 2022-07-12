package com.tabwu.SAP.production.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tabwu
 * @since 2022-07-11
 */
@Data
@TableName("yls_pro_bom")
@ApiModel(value = "ProBom对象", description = "")
@ExcelTarget("ProBom")
public class ProBom implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("BOM类型，1-封装BOM，2-测试BOM，3-封测一体BOM，默认-1")
    @Excel(name = "BOM类型，1-封装BOM，2-测试BOM，3-封测一体BOM，默认-1")
    private Integer type;

    @ApiModelProperty("子物料名称")
    @Excel(name = "子物料名称")
    private String subMaterialName;

    @ApiModelProperty("子物料code码")
    @Excel(name = "子物料code码")
    private String subMaterialCode;

    @ApiModelProperty("子物料数量")
    @Excel(name = "子物料数量")
    private Integer subMaterialNum;

    @ApiModelProperty("子物料单位")
    @Excel(name = "子物料单位")
    private String subMaterialUnit;

    @ApiModelProperty("父物料名称")
    @Excel(name = "父物料名称")
    private String parentMaterialName;

    @ApiModelProperty("父物料code码")
    @Excel(name = "父物料code码")
    private String parentMaterialCode;

    @ApiModelProperty("父物料数量")
    @Excel(name = "父物料数量")
    private Integer parentMaterialNum;

    @ApiModelProperty("父物料单位")
    @Excel(name = "父物料单位")
    private String parentMaterialUnit;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    @TableLogic
    private Integer isDelete;

}
