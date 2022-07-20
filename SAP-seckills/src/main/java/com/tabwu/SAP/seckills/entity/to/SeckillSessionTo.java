package com.tabwu.SAP.seckills.entity.to;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/20 17:20
 * @DESCRIPTION:
 */
@Data
public class SeckillSessionTo {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("场次名称")
    private String name;

    @ApiModelProperty("每日开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("每日结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("启用状态")
    private Boolean status;

    @ApiModelProperty("关联商品id集合")
    private List<Long> pids;

}
