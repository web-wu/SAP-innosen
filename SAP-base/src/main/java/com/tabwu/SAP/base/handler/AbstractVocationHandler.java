package com.tabwu.SAP.base.handler;

import com.tabwu.SAP.base.entity.Vocation;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/8/3 17:16
 * @DESCRIPTION:  责任链设计模式 处理 请假条
 */
public abstract class AbstractVocationHandler {
    // 最小请假时长1小时
    protected static final int VOCATION_LEVEL_0 = 60 * 60;
    // 一天24小时
    public static final int VOCATION_LEVEL_1 = 24 * 60 * 60;
    // 三天72小时
    public static final int VOCATION_LEVEL_2 = 3 * 24 * 60 * 60;
    // 七天168小时
    public static final int VOCATION_LEVEL_3 = 7 * 24 * 60 * 60;
    // 领导处理请假条时间最小值
    private int minHour;
    // 领导处理请假条时间最大值
    private int maxHour;
    // 当前领导是否有上级领导
    private AbstractVocationHandler nextVocattionHandler;

    // 该领导没有审批时间上限
    public AbstractVocationHandler(int minHour) {
        this.minHour = minHour;
    }

    // 设置领导审批开始时间、结束时间
    public AbstractVocationHandler(int minHour, int maxHour) {
        this.minHour = minHour;
        this.maxHour = maxHour;
    }

    public final void submitVocation(Vocation vocation) {
        int diffTime = vocation.getEndTtime().getSecond() - vocation.getStartTime().getSecond();

        if (diffTime >= VOCATION_LEVEL_0) {
            this.handlerVocation(vocation);
            if (nextVocattionHandler != null && diffTime > this.maxHour) {
                nextVocattionHandler.handlerVocation(vocation);
            }
        } else {
            // TODO 请假时长过短，不能处理
        }
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
