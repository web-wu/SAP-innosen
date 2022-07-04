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
    public Integer checkStockByMcode(WareStockTo wareStockTo) {
        // 物料类型m_type=3时，物料为成品，可售
        MaterialWare materialWare = baseMapper.selectOne(new QueryWrapper<MaterialWare>()
                .eq("m_type", 3)
                .eq("mid", wareStockTo.getMcode())
                .eq("lot", wareStockTo.getLot())
                .eq("ware_id", wareStockTo.getWareId())
                .eq("local_storage_id", wareStockTo.getLocalStorageId()));
        if (materialWare == null) {
            throw new CostomException(20004,"库存中没有指定条件的物料");
        }
        return materialWare.getStock();
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
