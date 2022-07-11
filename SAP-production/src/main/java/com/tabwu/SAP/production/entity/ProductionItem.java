package com.tabwu.SAP.production.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author tabwu
 * @since 2022-07-11
 */
@TableName("yls_production_item")
@ApiModel(value = "ProductionItem对象", description = "")
public class ProductionItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("父单据编号")
    private String pcode;

    @ApiModelProperty("物料类型")
    private Integer mtype;

    @ApiModelProperty("物料条码")
    private String mcode;

    @ApiModelProperty("物料名称")
    private String name;

    @ApiModelProperty("规格")
    private String param;

    @ApiModelProperty("物料数量")
    private Integer number;

    @ApiModelProperty("加工费单价")
    private BigDecimal price;

    @ApiModelProperty("加工费总价")
    private BigDecimal allPrice;

    @ApiModelProperty("已收数量")
    private Integer numbered;

    @ApiModelProperty("待收数量")
    private Integer numbering;

    @ApiModelProperty("物料批号")
    private String lot;

    @ApiModelProperty("仓库id")
    private String wareId;

    @ApiModelProperty("库位id")
    private String localStorageId;

    @ApiModelProperty("单据创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("单据修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    private Integer isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }
    public Integer getMtype() {
        return mtype;
    }

    public void setMtype(Integer mtype) {
        this.mtype = mtype;
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
    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
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
        return "ProductionItem{" +
            "id=" + id +
            ", pcode=" + pcode +
            ", mtype=" + mtype +
            ", mcode=" + mcode +
            ", name=" + name +
            ", param=" + param +
            ", number=" + number +
            ", price=" + price +
            ", allPrice=" + allPrice +
            ", numbered=" + numbered +
            ", numbering=" + numbering +
            ", lot=" + lot +
            ", wareId=" + wareId +
            ", localStorageId=" + localStorageId +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", isDelete=" + isDelete +
        "}";
    }
}
