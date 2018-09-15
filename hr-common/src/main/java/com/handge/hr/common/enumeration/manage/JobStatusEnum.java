package com.handge.hr.common.enumeration.manage;

/**
 * create by xc in 2018/08/31
 */
public enum JobStatusEnum {
    /**
     * 正式员工
     */
    REGULAR("01"),
    /**
     * 试用期
     */
    SHARKDOWN_PERIOD("02"),
    /**
     * 实习期
     */
    INTERNSHIP("03"),
    /**
     * 考察期
     */
    INVESTIGATION("04"),
    /**
     * 离职
     */
    DIMISSION("05"),
    /**
     * 外派
     */
    EXPATRIATE("06");

    private String value;

    public String getValue() {
        return value;
    }

    JobStatusEnum(String value) {
        this.value = value;
    }
}
