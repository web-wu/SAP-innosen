package com.tabwu.SAP.ware.service;

import com.tabwu.SAP.ware.entity.Material;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tabwu.SAP.ware.entity.vo.MaterialVo;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tabwu
 * @since 2022-06-11
 */
public interface IMaterialService extends IService<Material> {

    Map<String, Object> queryPage(int current, int size, MaterialVo materialVo);
}
