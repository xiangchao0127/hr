package com.handge.hr.common.enumeration.manage;

/**
 * Created by MaJianfu on 2018/6/25.
 */
public enum BooleanEnum {
    //否
    NO("0"),
    //是
    YES("1");

    private String value;

    BooleanEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
