package com.tabwu.SAP.sale.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author tabwu
 * @since 2022-07-04
 */
@TableName("yls_sale")
@ApiModel(value = "Sale对象", description = "")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("单据编号")
    private String code;

    @ApiModelProperty("订单号")
    private String ordersn;

    @ApiModelProperty("单据类型，1-销售订单，2-销售出库单，3-销售退货单，默认-1")
    private Integer type;

    @ApiModelProperty("客户")
    private String costomer;

    @ApiModelProperty("关联的订单")
    private String relationItem;

    @ApiModelProperty("交易凭据")
    private String txId;

    @ApiModelProperty("销售员")
    private String saler;

    @ApiModelProperty("税率，默认13%")
    private String tax;

    @ApiModelProperty("税额合计")
    private BigDecimal allTax;

    @ApiModelProperty("税前总价")
    private BigDecimal allPrice;

    @ApiModelProperty("税后总价=税前总价-税额合计")
    private BigDecimal totalPrice;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("单据状态，0-待付款，1-已付款，2-待出货，3-已完成，4-已取消，默认0")
    private Integer status;

    @ApiModelProperty("单据创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("单据修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("物料出库时间")
    private LocalDateTime outputTime;

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
    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getCostomer() {
        return costomer;
    }

    public void setCostomer(String costomer) {
        this.costomer = costomer;
    }
    public String getRelationItem() {
        return relationItem;
    }

    public void setRelationItem(String relationItem) {
        this.relationItem = relationItem;
    }
    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }
    public String getSaler() {
        return saler;
    }

    public void setSaler(String saler) {
        this.saler = saler;
    }
    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }
    public BigDecimal getAllTax() {
        return allTax;
    }

    public void setAllTax(BigDecimal allTax) {
        this.allTax = allTax;
    }
    public BigDecimal getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(BigDecimal allPrice) {
        this.allPrice = allPrice;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
    public LocalDateTime getOutputTime() {
        return outputTime;
    }

    public void setOutputTime(LocalDateTime outputTime) {
        this.outputTime = outputTime;
    }
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Sale{" +
            "id=" + id +
            ", code=" + code +
            ", ordersn=" + ordersn +
            ", type=" + type +
            ", costomer=" + costomer +
            ", relationItem=" + relationItem +
            ", txId=" + txId +
            ", saler=" + saler +
            ", tax=" + tax +
            ", allTax=" + allTax +
            ", allPrice=" + allPrice +
            ", totalPrice=" + totalPrice +
            ", remark=" + remark +
            ", status=" + status +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", outputTime=" + outputTime +
            ", isDelete=" + isDelete +
        "}";
    }
}
