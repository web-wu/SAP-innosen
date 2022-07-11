package com.tabwu.SAP.production.service.impl;

import com.tabwu.SAP.production.entity.Production;
import com.tabwu.SAP.production.mapper.ProductionMapper;
import com.tabwu.SAP.production.service.IProductionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author tabwu
 * @since 2022-07-11
 */
@Service
public class ProductionServiceImpl extends ServiceImpl<ProductionMapper, Production> implements IProductionService {

}
