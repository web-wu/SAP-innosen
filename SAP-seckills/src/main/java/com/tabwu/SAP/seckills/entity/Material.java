package com.tabwu.SAP.seckills.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author tabwu
 * @since 2022-06-19
 */
@Data
@TableName("yls_material")
@ApiModel(value = "Material对象", description = "")
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("物料条码")
    private String code;

    @ApiModelProperty("产品类型id,1-晶圆，2-半成品，3-成品")
    private Integer typeId;

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

    @ApiModelProperty("计量单位id")
    private String unitId;

    @ApiModelProperty("状态 0-禁用  1-启用")
    private Integer status;

    @ApiModelProperty("制造商")
    private String mfrs;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("一级分类")
    private String category1;

    @ApiModelProperty("二级分类")
    private String category2;

    @ApiModelProperty("三级分类")
    private String category3;

    @ApiModelProperty("自定义1")
    private String otherField1;

    @ApiModelProperty("自定义2")
    private String otherField2;

    @ApiModelProperty("自定义3")
    private String otherField3;

    @ApiModelProperty("自定义4")
    private String otherField4;

    @ApiModelProperty("自定义5")
    private String otherField5;

    @ApiModelProperty("自定义6")
    private String otherField6;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    @TableLogic
    private Integer isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }
    public String getMaterialGroupId() {
        return materialGroupId;
    }

    public void setMaterialGroupId(String materialGroupId) {
        this.materialGroupId = materialGroupId;
    }
    public String getPackageVerssion() {
        return packageVerssion;
    }

    public void setPackageVerssion(String packageVerssion) {
        this.packageVerssion = packageVerssion;
    }
    public String getTestVerssion() {
        return testVerssion;
    }

    public void setTestVerssion(String testVerssion) {
        this.testVerssion = testVerssion;
    }
    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }
    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getMfrs() {
        return mfrs;
    }

    public void setMfrs(String mfrs) {
        this.mfrs = mfrs;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }
    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }
    public String getCategory3() {
        return category3;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }
    public String getOtherField1() {
        return otherField1;
    }

    public void setOtherField1(String otherField1) {
        this.otherField1 = otherField1;
    }
    public String getOtherField2() {
        return otherField2;
    }

    public void setOtherField2(String otherField2) {
        this.otherField2 = otherField2;
    }
    public String getOtherField3() {
        return otherField3;
    }

    public void setOtherField3(String otherField3) {
        this.otherField3 = otherField3;
    }
    public String getOtherField4() {
        return otherField4;
    }

    public void setOtherField4(String otherField4) {
        this.otherField4 = otherField4;
    }
    public String getOtherField5() {
        return otherField5;
    }

    public void setOtherField5(String otherField5) {
        this.otherField5 = otherField5;
    }
    public String getOtherField6() {
        return otherField6;
    }

    public void setOtherField6(String otherField6) {
        this.otherField6 = otherField6;
    }
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Material{" +
            "id=" + id +
            ", code=" + code +
            ", typeId=" + typeId +
            ", name=" + name +
            ", packaging=" + packaging +
            ", materialGroupId=" + materialGroupId +
            ", packageVerssion=" + packageVerssion +
            ", testVerssion=" + testVerssion +
            ", bin=" + bin +
            ", unitId=" + unitId +
            ", status=" + status +
            ", mfrs=" + mfrs +
            ", remark=" + remark +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", category1=" + category1 +
            ", category2=" + category2 +
            ", category3=" + category3 +
            ", otherField1=" + otherField1 +
            ", otherField2=" + otherField2 +
            ", otherField3=" + otherField3 +
            ", otherField4=" + otherField4 +
            ", otherField5=" + otherField5 +
            ", otherField6=" + otherField6 +
            ", isDelete=" + isDelete +
        "}";
    }
}
