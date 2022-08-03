package com.tabwu.SAP.base.handler;

import com.tabwu.SAP.base.entity.Vocation;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/8/3 17:16
 * @DESCRIPTION:  责任链设计模式 处理 请假条
 */
public abstract class AbstractVocationHandler {
    // 一天8小时
    protected static final int VOCATION_LEVEL_1 = 8;
    // 三天24小时
    protected static final int VOCATION_LEVEL_2 = 24;
    // 七天56小时
    protected static final int VOCATION_LEVEL_3 = 56;
    // 领导处理请假条时间最小值
    private int minHour;
    // 领导处理请假条时间最大值
    private int maxHour;
    // 当前领导是否有上级领导
    private AbstractVocationHandler nextVocattionHandler;


    public final void submitVocation(Vocation vocation) {
        int i = vocation.getEndTtime() - vocation.getStartTime();
        if ()
    }

    protected abstract void handlerVocation(Vocation vocation);

    public int getMinHour() {
        return minHour;
    }

    public void setMinHour(int minHour) {
        this.minHour = minHour;
    }

    public int getMaxHour() {
        return maxHour;
    }

    public void setMaxHour(int maxHour) {
        this.maxHour = maxHour;
    }

    public AbstractVocationHandler getNextVocattionHandler() {
        return nextVocattionHandler;
    }

    public void setNextVocattionHandler(AbstractVocationHandler nextVocattionHandler) {
        this.nextVocattionHandler = nextVocattionHandler;
    }
}
