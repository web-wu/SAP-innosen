package com.tabwu.SAP.base.service;

import com.tabwu.SAP.common.entity.Logs;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tabwu.SAP.base.entity.vo.LogQueryVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tabwu
 * @since 2022-06-19
 */
public interface ILogsService extends IService<Logs> {

    Map<String, Object> listPage(LogQueryVo logQueryVo);
}
