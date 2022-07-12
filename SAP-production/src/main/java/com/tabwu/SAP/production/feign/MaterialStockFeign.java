package com.tabwu.SAP.production.feign;

import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.production.entity.to.WareStockTo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/4 14:03
 * @DESCRIPTION:
 */
@FeignClient(value = "ware-service")
public interface MaterialStockFeign {

    @PostMapping("/ware/material-ware/checkStock/{mcode}")
    @ApiOperation(value = "根据物料条码、类型、仓库、库位。批号检查仓库中的物料数量")
    R checkStockByMcode(@ApiParam(name = "wareStockTos",value = "WareStockTo集合",required = true)
                                   @RequestBody List<WareStockTo> wareStockTos);


    @PostMapping("/ware/material-ware/reduceStock")
    @ApiOperation(value = "根据物料条码、类型、仓库、库位。批号扣减仓库中的物料数量")
    R reduceWareStockByCondition(@ApiParam(name = "wareStockTos",value = "wareStockTos",required = true)
                                        @RequestBody List<WareStockTo> wareStockTos);
}
