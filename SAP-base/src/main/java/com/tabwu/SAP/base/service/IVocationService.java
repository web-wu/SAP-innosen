package com.tabwu.SAP.base.service;

import com.tabwu.SAP.base.entity.Vocation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tabwu.SAP.base.entity.vo.VocationQueryVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tabwu
 * @since 2022-08-03
 */
public interface IVocationService extends IService<Vocation> {

    Map<String, Object> listPage(VocationQueryVo vocationQueryVo);

    void approveVocation(String id,HttpServletRequest request);
}
