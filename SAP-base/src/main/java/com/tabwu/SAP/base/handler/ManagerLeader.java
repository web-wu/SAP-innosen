package com.tabwu.SAP.base.handler;

import com.tabwu.SAP.base.entity.Vocation;
import com.tabwu.SAP.base.service.IVocationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/8/5 16:25
 * @DESCRIPTION:
 */
public class ManagerLeader extends AbstractVocationHandler{

    private final String approver;

    @Autowired
    private IVocationService vocationService;

    public ManagerLeader(String approver) {
        super(ManagerLeader.VOCATION_LEVEL_2, ManagerLeader.VOCATION_LEVEL_3);
        this.approver = approver;
    }

    @Override
    protected void handlerVocation(Vocation vocation) {
        vocation.setStatus(1);
        vocation.setApprove2(approver);
        vocationService.updateById(vocation);
    }
}
