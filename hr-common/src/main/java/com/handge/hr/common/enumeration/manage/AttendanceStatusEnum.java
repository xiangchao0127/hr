package com.handge.hr.common.enumeration.manage;

/**
 * @author liuqian
 * @date 2018/9/3
 * @Description: 考勤状态
 */
public enum AttendanceStatusEnum {
    //正常
    NORMAL("01"),
    //迟到
    BE_LATE("02"),
    //未打卡
    NOT_PUNCH_CARD("03"),
    //早退
    LEAVE_EARLY("04"),
    //未到岗
    NOT_ARRIVAL("05");

    private String value;
    AttendanceStatusEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
