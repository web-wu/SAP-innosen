package com.tabwu.SAP.base.handler;

import com.tabwu.SAP.base.entity.Vocation;
import com.tabwu.SAP.base.service.IVocationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/8/4 17:24
 * @DESCRIPTION:
 */
public class GroupLeader extends AbstractVocationHandler{

    private final String approver;

    @Autowired
    private IVocationService vocationService;

    public GroupLeader(String approver) {
        super(GroupLeader.VOCATION_LEVEL_1, GroupLeader.VOCATION_LEVEL_2);
        this.approver = approver;
    }

    @Override
    protected void handlerVocation(Vocation vocation) {
        vocation.setStatus(1);
        vocation.setApprove1(approver);
        vocationService.updateById(vocation);
    }
}
