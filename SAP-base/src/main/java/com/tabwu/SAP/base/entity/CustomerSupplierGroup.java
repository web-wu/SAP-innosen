package com.tabwu.SAP.base.entity;

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
 * @since 2022-06-19
 */
@TableName("yls_customer_supplier_group")
@ApiModel(value = "CustomerSupplierGroup对象", description = "")
public class CustomerSupplierGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("组名称")
    private String groupName;

    @ApiModelProperty("组描述")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CustomerSupplierGroup{" +
            "id=" + id +
            ", groupName=" + groupName +
            ", description=" + description +
        "}";
    }
}
