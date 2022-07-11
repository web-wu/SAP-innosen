package com.tabwu.SAP.production.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author tabwu
 * @since 2022-07-11
 */
@TableName("yls_production")
@ApiModel(value = "Production对象", description = "")
public class Production implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("单据编号")
    private String code;

    @ApiModelProperty("生产类型，1-封装，2-测试，3-封测一体，默认-1")
    private Integer pType;

    @ApiModelProperty("单据类型，1-生产订单，2-生成取料单，3-生成入库单，默认-1")
    private Integer type;

    @ApiModelProperty("服务提供方公司名称")
    private String company;

    @ApiModelProperty("业务员")
    private String clerk;

    @ApiModelProperty("关联的订单")
    private String relationItem;

    @ApiModelProperty("bom的id")
    private String bomId;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("单据状态，0-待出料，1-已出料，2-待入库，3-已入库，4-已取消，默认0")
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
    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    public String getClerk() {
        return clerk;
    }

    public void setClerk(String clerk) {
        this.clerk = clerk;
    }
    public String getRelationItem() {
        return relationItem;
    }

    public void setRelationItem(String relationItem) {
        this.relationItem = relationItem;
    }
    public String getBomId() {
        return bomId;
    }

    public void setBomId(String bomId) {
        this.bomId = bomId;
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
        return "Production{" +
            "id=" + id +
            ", code=" + code +
            ", pType=" + pType +
            ", type=" + type +
            ", company=" + company +
            ", clerk=" + clerk +
            ", relationItem=" + relationItem +
            ", bomId=" + bomId +
            ", remark=" + remark +
            ", status=" + status +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", outputTime=" + outputTime +
            ", isDelete=" + isDelete +
        "}";
    }
}
