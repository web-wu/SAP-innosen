package com.tabwu.SAP.ware.entity;

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
@TableName("yls_ware_storage")
@ApiModel(value = "WareStorage对象", description = "")
public class WareStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("仓库id")
    private String wid;

    @ApiModelProperty("库位id")
    private String lid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "WareStorage{" +
            "id=" + id +
            ", wid=" + wid +
            ", lid=" + lid +
        "}";
    }
}
