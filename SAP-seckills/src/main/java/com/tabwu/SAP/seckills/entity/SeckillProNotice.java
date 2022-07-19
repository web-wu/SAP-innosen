package com.tabwu.SAP.seckills.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author tabwu
 * @since 2022-07-19
 */
@TableName("yls_seckill_pro_notice")
@ApiModel(value = "SeckillProNotice对象", description = "秒杀商品通知订阅")
public class SeckillProNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("member_id")
    private Long uid;

    @ApiModelProperty("商品id")
    private Long pid;

    @ApiModelProperty("活动场次id")
    private Long sessionId;

    @ApiModelProperty("订阅时间")
    private LocalDateTime subcribeTime;

    @ApiModelProperty("发送时间")
    private LocalDateTime sendTime;

    @ApiModelProperty("通知方式[0-短信，1-邮件]")
    private Boolean noticeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
    public LocalDateTime getSubcribeTime() {
        return subcribeTime;
    }

    public void setSubcribeTime(LocalDateTime subcribeTime) {
        this.subcribeTime = subcribeTime;
    }
    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
    public Boolean getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Boolean noticeType) {
        this.noticeType = noticeType;
    }

    @Override
    public String toString() {
        return "SeckillProNotice{" +
            "id=" + id +
            ", uid=" + uid +
            ", pid=" + pid +
            ", sessionId=" + sessionId +
            ", subcribeTime=" + subcribeTime +
            ", sendTime=" + sendTime +
            ", noticeType=" + noticeType +
        "}";
    }
}
