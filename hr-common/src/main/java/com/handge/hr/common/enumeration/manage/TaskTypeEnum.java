package com.handge.hr.common.enumeration.manage;

/**
 * Created by MaJianfu on 2018/6/25.
 */
public enum TaskTypeEnum {
    //本职工作
    DUTIES("A"),
    //上级指派工作
    ASSIGN("B"),
    //自主性工作
    TONOMY("C"),
    //突发临时工作
    TEMPORARY("D"),
    //其他
    OTHER("O");

    private String value;

    TaskTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
