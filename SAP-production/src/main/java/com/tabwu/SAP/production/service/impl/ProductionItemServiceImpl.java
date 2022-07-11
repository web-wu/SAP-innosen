package com.tabwu.SAP.production.service.impl;

import com.tabwu.SAP.production.entity.ProductionItem;
import com.tabwu.SAP.production.mapper.ProductionItemMapper;
import com.tabwu.SAP.production.service.IProductionItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author tabwu
 * @since 2022-07-11
 */
@Service
public class ProductionItemServiceImpl extends ServiceImpl<ProductionItemMapper, ProductionItem> implements IProductionItemService {

}
