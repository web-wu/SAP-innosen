package com.tabwu.SAP.production.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/11 16:10
 * @DESCRIPTION:
 */
@Data
@ApiModel("BomVo对象")
public class BomVo {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("BOM类型，1-封装BOM，2-测试BOM，3-封测一体BOM，默认-1")
    private Integer type;

    @ApiModelProperty("子物料id")
    private String subMaterialId;

    @ApiModelProperty("子物料code码")
    private String subMaterialCode;

    @ApiModelProperty("子物料数量")
    private Integer subMaterialNum;

    @ApiModelProperty("子物料单位")
    private String subMaterialUnit;

    @ApiModelProperty("父物料集合")
    private List<ParentMaterial> parentMaterials;


    @Data
    public static class ParentMaterial {

        @ApiModelProperty("父物料id")
        private String parentMaterialId;

        @ApiModelProperty("父物料code码")
        private String parentMaterialCode;

        @ApiModelProperty("父物料数量")
        private Integer parentMaterialNum;

        @ApiModelProperty("父物料单位")
        private String parentMaterialUnit;
    }
}
