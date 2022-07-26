package com.tabwu.SAP.sale.feign.callback;

import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.sale.entity.to.WareStockTo;
import com.tabwu.SAP.sale.feign.MaterialStockFeign;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/26 16:23
 * @DESCRIPTION:
 */
@Component
public class MaterialStockCallbackService implements MaterialStockFeign {
    @Override
    public R checkStockByMcode(List<WareStockTo> wareStockTos) {
        return R.ok().message("服务降级处理");
    }

    @Override
    public R reduceWareStockByCondition(List<WareStockTo> wareStockTos) {
        return R.ok().message("服务降级处理");
    }
}
