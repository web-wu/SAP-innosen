package com.tabwu.SAP.base.service.impl;

import com.tabwu.SAP.base.entity.Logs;
import com.tabwu.SAP.base.mapper.LogsMapper;
import com.tabwu.SAP.base.service.ILogsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tabwu
 * @since 2022-06-19
 */
@Service
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements ILogsService {

}
