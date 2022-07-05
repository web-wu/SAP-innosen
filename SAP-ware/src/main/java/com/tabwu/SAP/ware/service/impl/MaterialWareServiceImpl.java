package com.tabwu.SAP.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.ware.entity.MaterialWare;
import com.tabwu.SAP.ware.entity.to.WareStockTo;
import com.tabwu.SAP.ware.mapper.MaterialWareMapper;
import com.tabwu.SAP.ware.service.IMaterialWareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tabwu
 * @since 2022-06-11
 */
@Service
public class MaterialWareServiceImpl extends ServiceImpl<MaterialWareMapper, MaterialWare> implements IMaterialWareService {

    @Override
    public boolean checkStockByMcode(List<WareStockTo> wareStockTos) {
        for (WareStockTo wareStockTo : wareStockTos) {
            // 物料类型m_type=3时，物料为成品，可售
            MaterialWare materialWare = baseMapper.selectOne(new QueryWrapper<MaterialWare>()
                    .eq("m_type", 3)
                    .eq("mid", wareStockTo.getMcode())
                    .eq("lot", wareStockTo.getLot())
                    .eq("wid", wareStockTo.getWareId())
                    .eq("lid", wareStockTo.getLocalStorageId()));
            if (materialWare == null) {
                throw new CostomException(20004,"库存中没有指定条件的物料");
            }
            //当订购量大于库存量时是不能生成销售订单的，返回false
            if (wareStockTo.getNumber() > materialWare.getStock()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean reduceWareStockByCondition(List<WareStockTo> wareStockTos) {
        for (WareStockTo wareStockTo : wareStockTos) {
            long update = baseMapper.reduceWareStockByCondition(wareStockTo.getWareId(),wareStockTo.getMcode(),
                    wareStockTo.getLocalStorageId(),wareStockTo.getLot(),wareStockTo.getNumber());
            if (update <= 0) {
                throw new CostomException(20004,"扣减库存失败!!!");
            }
        }
        return true;
    }
}
