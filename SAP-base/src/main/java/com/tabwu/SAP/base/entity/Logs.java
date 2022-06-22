package com.tabwu.SAP.base.entity;

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
 * @since 2022-06-19
 */
@TableName("yls_logs")
@ApiModel(value = "Logs对象", description = "")
public class Logs implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("操作模块名称")
    private String module;

    @ApiModelProperty("操作方法")
    private String method;

    @ApiModelProperty("方法入参")
    private String inputParams;

    @ApiModelProperty("方法返回值")
    private String outputParams;

    @ApiModelProperty("操作类型")
    private String operateType;

    @ApiModelProperty("操作详情")
    private String content;

    @ApiModelProperty("客户端IP")
    private String clientIp;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    private Integer isDelete;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTtime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    public String getInputParams() {
        return inputParams;
    }

    public void setInputParams(String inputParams) {
        this.inputParams = inputParams;
    }
    public String getOutputParams() {
        return outputParams;
    }

    public void setOutputParams(String outputParams) {
        this.outputParams = outputParams;
    }
    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
    public LocalDateTime getCreateTtime() {
        return createTtime;
    }

    public void setCreateTtime(LocalDateTime createTtime) {
        this.createTtime = createTtime;
    }
    public LocalDateTime getUpdateTtime() {
        return updateTtime;
    }

    public void setUpdateTtime(LocalDateTime updateTtime) {
        this.updateTtime = updateTtime;
    }

    @Override
    public String toString() {
        return "Logs{" +
            "id=" + id +
            ", username=" + username +
            ", module=" + module +
            ", method=" + method +
            ", inputParams=" + inputParams +
            ", outputParams=" + outputParams +
            ", operateType=" + operateType +
            ", content=" + content +
            ", clientIp=" + clientIp +
            ", isDelete=" + isDelete +
            ", createTtime=" + createTtime +
            ", updateTtime=" + updateTtime +
        "}";
    }
}
