package com.tabwu.SAP.base.handler;

import com.tabwu.SAP.base.entity.Vocation;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/8/4 17:24
 * @DESCRIPTION:
 */
public class GroupLeader extends AbstractVocationHandler{

    private String spprove;

    public GroupLeader(String spprove) {
        super(GroupLeader.VOCATION_LEVEL_1, GroupLeader.VOCATION_LEVEL_2);
        this.spprove = spprove;
    }

    @Override
    protected void handlerVocation(Vocation vocation) {
        vocation.setStatus(1);
        vocation.setApprove1(spprove);
    }
}
