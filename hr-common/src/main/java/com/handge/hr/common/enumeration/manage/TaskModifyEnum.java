package com.handge.hr.common.enumeration.manage;

/**
 * create by xc in 2018/09/05
 */
public enum TaskModifyEnum {
    /**
     * 任务名称
     */
    TASKNAME("任务名称"),
    /**
     * 任务内容
     */
    CONTENT("任务内容"),
    /**
     * 任务类型
     */
    TYPE("任务类型"),
    /**
     * 部门ID
     */
    DEPARTMENTID("部门ID"),
    /**
     * 计划开始时间
     */
    PLANSTARTTIME("计划开始时间"),
    /**
     * 计划结束时间
     */
    PLANENDTIME("计划结束时间"),
    /**
     * 难度
     */
    DIFFICULT("任务难度"),
    /**
     * 紧急程度
     */
    URGENCY("紧急程度"),
    /**
     * 工作量
     */
    WORKLOAD("工作量"),
    /**
     * 任务负责人
     */
    RECEIVER("任务负责人");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    TaskModifyEnum(String value) {
        this.value = value;
    }
}
