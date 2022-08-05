package com.tabwu.SAP.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tabwu.SAP.base.entity.To.UserTo;
import com.tabwu.SAP.base.entity.Vocation;
import com.tabwu.SAP.base.entity.vo.VocationQueryVo;
import com.tabwu.SAP.base.feign.UserFeignService;
import com.tabwu.SAP.base.handler.GeneralManagerLeader;
import com.tabwu.SAP.base.handler.GroupLeader;
import com.tabwu.SAP.base.handler.ManagerLeader;
import com.tabwu.SAP.base.mapper.VocationMapper;
import com.tabwu.SAP.base.service.IVocationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tabwu.SAP.common.entity.LoginUser;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.utils.JwtUtils;
import com.tabwu.SAP.common.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author tabwu
 * @since 2022-08-03
 */
@Service
public class VocationServiceImpl extends ServiceImpl<VocationMapper, Vocation> implements IVocationService {

    @Autowired
    private UserFeignService userFeignService;

    @Override
    public Map<String, Object> listPage(VocationQueryVo vocationQueryVo) {
        QueryWrapper<Vocation> wrapper = new QueryWrapper<>();
        Page<Vocation> page = new Page<>(vocationQueryVo.getCurrent(), vocationQueryVo.getSize());

        if (vocationQueryVo.getType() != null) {
            wrapper.eq("type",vocationQueryVo.getType());
        }

        if (vocationQueryVo.getStatus() != null) {
            wrapper.eq("status",vocationQueryVo.getStatus());
        }

        Page<Vocation> vocationPage = this.page(page, wrapper);

        return PageUtil.queryPage(vocationPage);
    }

    @Override
    public void approveVocation(String id, HttpServletRequest request) {
        Vocation vocation = this.getById(id);

        LoginUser loginUser = JwtUtils.getLoginUserByToken(request);

        GroupLeader groupLeader = new GroupLeader(loginUser.getUsername());

        if (!StringUtils.isEmpty(loginUser.getLeaderId())) {
            R r = userFeignService.findLeaderByLeaderId(loginUser.getLeaderId());
            UserTo leader = JSON.parseObject(JSON.toJSONString(r.getData().get("userTo")), UserTo.class);
            ManagerLeader managermentLeader = new ManagerLeader(leader.getUsername());
            groupLeader.setNextVocattionHandler(managermentLeader);
            if (!StringUtils.isEmpty(leader.getLeaderId())) {
                R r1 = userFeignService.findLeaderByLeaderId(leader.getLeaderId());
                UserTo nextLeader = JSON.parseObject(JSON.toJSONString(r1.getData().get("userTo")), UserTo.class);
                GeneralManagerLeader generalManagerLeader = new GeneralManagerLeader(nextLeader.getUsername());
                managermentLeader.setNextVocattionHandler(generalManagerLeader);
            }
        }

        groupLeader.submitVocation(vocation);
    }
}
