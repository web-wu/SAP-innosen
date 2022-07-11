package com.tabwu.SAP.production.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author tabwu
 * @since 2022-07-11
 */
@TableName("yls_pro_bom")
@ApiModel(value = "ProBom对象", description = "")
public class ProBom implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @ApiModelProperty("父物料id")
    private String parentMaterialId;

    @ApiModelProperty("父物料code码")
    private String parentMaterialCode;

    @ApiModelProperty("父物料数量")
    private Integer parentMaterialNum;

    @ApiModelProperty("父物料单位")
    private String parentMaterialUnit;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    @TableLogic
    private Integer isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getSubMaterialId() {
        return subMaterialId;
    }

    public void setSubMaterialId(String subMaterialId) {
        this.subMaterialId = subMaterialId;
    }
    public String getSubMaterialCode() {
        return subMaterialCode;
    }

    public void setSubMaterialCode(String subMaterialCode) {
        this.subMaterialCode = subMaterialCode;
    }
    public Integer getSubMaterialNum() {
        return subMaterialNum;
    }

    public void setSubMaterialNum(Integer subMaterialNum) {
        this.subMaterialNum = subMaterialNum;
    }
    public String getSubMaterialUnit() {
        return subMaterialUnit;
    }

    public void setSubMaterialUnit(String subMaterialUnit) {
        this.subMaterialUnit = subMaterialUnit;
    }
    public String getParentMaterialId() {
        return parentMaterialId;
    }

    public void setParentMaterialId(String parentMaterialId) {
        this.parentMaterialId = parentMaterialId;
    }
    public String getParentMaterialCode() {
        return parentMaterialCode;
    }

    public void setParentMaterialCode(String parentMaterialCode) {
        this.parentMaterialCode = parentMaterialCode;
    }
    public Integer getParentMaterialNum() {
        return parentMaterialNum;
    }

    public void setParentMaterialNum(Integer parentMaterialNum) {
        this.parentMaterialNum = parentMaterialNum;
    }
    public String getParentMaterialUnit() {
        return parentMaterialUnit;
    }

    public void setParentMaterialUnit(String parentMaterialUnit) {
        this.parentMaterialUnit = parentMaterialUnit;
    }
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "ProBom{" +
            "id=" + id +
            ", type=" + type +
            ", subMaterialId=" + subMaterialId +
            ", subMaterialCode=" + subMaterialCode +
            ", subMaterialNum=" + subMaterialNum +
            ", subMaterialUnit=" + subMaterialUnit +
            ", parentMaterialId=" + parentMaterialId +
            ", parentMaterialCode=" + parentMaterialCode +
            ", parentMaterialNum=" + parentMaterialNum +
            ", parentMaterialUnit=" + parentMaterialUnit +
            ", isDelete=" + isDelete +
        "}";
    }
}
