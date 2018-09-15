package com.handge.hr.common.enumeration.manage;

/**
 * Created by MaJianfu on 2018/6/25.
 */
public enum NumberTypeEnum {
    //项目
    PROJECT("P","项目"),
    //任务
    TASK("T","任务");

    private String value;
    private String description;

    NumberTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
