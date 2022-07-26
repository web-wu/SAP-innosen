package com.tabwu.SAP.sale.feign.callback;

import com.tabwu.SAP.common.entity.Logs;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.sale.feign.LogFeignService;
import org.springframework.stereotype.Component;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/26 16:18
 * @DESCRIPTION:
 */
@Component
public class LogCallbackService implements LogFeignService {
    @Override
    public R addLog(Logs logs) {
        return R.ok().message("服务降级处理");
    }
}
