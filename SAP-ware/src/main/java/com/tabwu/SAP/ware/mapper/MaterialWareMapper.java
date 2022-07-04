package com.tabwu.SAP.ware.mapper;

import com.tabwu.SAP.ware.entity.MaterialWare;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tabwu
 * @since 2022-06-11
 */
public interface MaterialWareMapper extends BaseMapper<MaterialWare> {

    long reduceWareStockByCondition(String wareId, String mcode, String localStorageId, String lot,int number);
}
