package com.tabwu.SAP.ware.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("yls_material_ware")
@ApiModel(value = "MaterialWare对象", description = "")
public class MaterialWare implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("产品id")
    private String mid;

    @ApiModelProperty("产品批号")
    private String lot;

    @ApiModelProperty("仓库id")
    private String wid;

    @ApiModelProperty("库位id")
    private String lid;

    @ApiModelProperty("产品数量")
    private Integer stock;

    @ApiModelProperty("锁定库存  1--库存锁定，0--未锁定-默认")
    private Integer stockLocked;

    @ApiModelProperty("创建时间 入库时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("出库时间")
    private LocalDateTime outputTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }
    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }
    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Integer getStockLocked() {
        return stockLocked;
    }

    public void setStockLocked(Integer stockLocked) {
        this.stockLocked = stockLocked;
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

    @Override
    public String toString() {
        return "MaterialWare{" +
            "id=" + id +
            ", mid=" + mid +
            ", lot=" + lot +
            ", wid=" + wid +
            ", lid=" + lid +
            ", stock=" + stock +
            ", stockLocked=" + stockLocked +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", outputTime=" + outputTime +
        "}";
    }
}
