package com.handge.hr.common.enumeration.manage;

/**
 * create by xc in xc
 */
public enum  ProjectModifyEnum {
    /**
     * 项目名称
     */
    NAME("项目名称"),
    /**
     * 项目类型
     */
    TYPE("项目类型"),
    /**
     * 计划开始时间
     */
    PLANSTARTTIME("计划开始时间"),
    /**
     * 计划结束时间
     */
    PLANENDTIME("计划结束时间"),
    /**
     * 项目负责人
     */
    PRINCIPAL("项目负责人"),
    /**
     * 项目备注
     */
    REMARK("项目备注"),
    /**
     * 项目描述
     */
    DESCRIPTION("项目描述"),
    /**
     * 项目编号
     */
    NUMBER("项目编号");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    ProjectModifyEnum(String value){
        this.value = value;
    }

}
