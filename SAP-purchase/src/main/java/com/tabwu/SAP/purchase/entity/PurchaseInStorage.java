package com.tabwu.SAP.purchase.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author tabwu
 * @since 2022-06-23
 */
@TableName("yls_purchase_in_storage")
@ApiModel(value = "PurchaseInStorage对象", description = "")
public class PurchaseInStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("单据编号")
    private String code;

    @ApiModelProperty("关联单据")
    private String relationItme;

    @ApiModelProperty("供应商")
    private String supplier;

    @ApiModelProperty("仓库id")
    private String wareId;

    @ApiModelProperty("库位id")
    private String localStorageId;

    @ApiModelProperty("物料条码")
    private String mcode;

    @ApiModelProperty("物料名称")
    private String name;

    @ApiModelProperty("规格")
    private String param;

    @ApiModelProperty("采购数量")
    private Integer number;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("税前总价")
    private BigDecimal allPrice;

    @ApiModelProperty("税率")
    private String tax;

    @ApiModelProperty("税额")
    private BigDecimal taxPrice;

    @ApiModelProperty("税额合计")
    private BigDecimal allTaxPrice;

    @ApiModelProperty("已收数量")
    private Integer numbered;

    @ApiModelProperty("待收数量")
    private Integer numbering;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("单据状态，0-未完成，1-已取消，2-已完成，默认0")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("跟新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
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
    public String getRelationItme() {
        return relationItme;
    }

    public void setRelationItme(String relationItme) {
        this.relationItme = relationItme;
    }
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    public String getWareId() {
        return wareId;
    }

    public void setWareId(String wareId) {
        this.wareId = wareId;
    }
    public String getLocalStorageId() {
        return localStorageId;
    }

    public void setLocalStorageId(String localStorageId) {
        this.localStorageId = localStorageId;
    }
    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public BigDecimal getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(BigDecimal allPrice) {
        this.allPrice = allPrice;
    }
    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }
    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }
    public BigDecimal getAllTaxPrice() {
        return allTaxPrice;
    }

    public void setAllTaxPrice(BigDecimal allTaxPrice) {
        this.allTaxPrice = allTaxPrice;
    }
    public Integer getNumbered() {
        return numbered;
    }

    public void setNumbered(Integer numbered) {
        this.numbered = numbered;
    }
    public Integer getNumbering() {
        return numbering;
    }

    public void setNumbering(Integer numbering) {
        this.numbering = numbering;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "PurchaseInStorage{" +
            "id=" + id +
            ", code=" + code +
            ", relationItme=" + relationItme +
            ", supplier=" + supplier +
            ", wareId=" + wareId +
            ", localStorageId=" + localStorageId +
            ", mcode=" + mcode +
            ", name=" + name +
            ", param=" + param +
            ", number=" + number +
            ", price=" + price +
            ", allPrice=" + allPrice +
            ", tax=" + tax +
            ", taxPrice=" + taxPrice +
            ", allTaxPrice=" + allTaxPrice +
            ", numbered=" + numbered +
            ", numbering=" + numbering +
            ", remark=" + remark +
            ", status=" + status +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", isDelete=" + isDelete +
        "}";
    }
}
