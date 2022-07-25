package com.tabwu.SAP.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tabwu.SAP.common.entity.Logs;
import com.tabwu.SAP.base.entity.vo.LogQueryVo;
import com.tabwu.SAP.base.mapper.LogsMapper;
import com.tabwu.SAP.base.service.ILogsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tabwu.SAP.common.utils.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author tabwu
 * @since 2022-06-19
 */
@Service
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements ILogsService {

    @Override
    public Map<String, Object> listPage(LogQueryVo logQueryVo) {
        Page<Logs> page = new Page<>(logQueryVo.getCurrent(), logQueryVo.getSize());
        QueryWrapper<Logs> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(logQueryVo.getUsername())) {
            wrapper.eq("username",logQueryVo.getUsername());
        }
        if (!StringUtils.isEmpty(logQueryVo.getModule())) {
            wrapper.eq("module",logQueryVo.getModule());
        }
        if (!StringUtils.isEmpty(logQueryVo.getOperateType())) {
            wrapper.eq("operate_type",logQueryVo.getOperateType());
        }
        this.page(page,wrapper);

        return PageUtil.queryPage(page);
    }
}
