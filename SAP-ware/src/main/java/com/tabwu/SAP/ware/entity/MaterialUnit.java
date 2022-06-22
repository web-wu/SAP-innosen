package com.tabwu.SAP.ware.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author tabwu
 * @since 2022-06-11
 */
@TableName("yls_material_unit")
@ApiModel(value = "MaterialUnit对象", description = "")
public class MaterialUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("基础单位")
    private String basicUnit;

    @ApiModelProperty("副单位1")
    private String otherUnit1;

    @ApiModelProperty("副单位2")
    private String otherUnit2;

    @ApiModelProperty("副单位3")
    private String otherUnit3;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    @TableLogic
    private Integer isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getBasicUnit() {
        return basicUnit;
    }

    public void setBasicUnit(String basicUnit) {
        this.basicUnit = basicUnit;
    }
    public String getOtherUnit1() {
        return otherUnit1;
    }

    public void setOtherUnit1(String otherUnit1) {
        this.otherUnit1 = otherUnit1;
    }
    public String getOtherUnit2() {
        return otherUnit2;
    }

    public void setOtherUnit2(String otherUnit2) {
        this.otherUnit2 = otherUnit2;
    }
    public String getOtherUnit3() {
        return otherUnit3;
    }

    public void setOtherUnit3(String otherUnit3) {
        this.otherUnit3 = otherUnit3;
    }
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "MaterialUnit{" +
            "id=" + id +
            ", basicUnit=" + basicUnit +
            ", otherUnit1=" + otherUnit1 +
            ", otherUnit2=" + otherUnit2 +
            ", otherUnit3=" + otherUnit3 +
            ", isDelete=" + isDelete +
        "}";
    }
}
