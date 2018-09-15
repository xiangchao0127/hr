package com.handge.hr.common.enumeration.manage;

/**
 * Created by MaJianfu on 2018/6/25.
 */
public enum ProjectEventEnum {
    //创建
    CREATE("01"),
    //编辑
    MODIFY("02"),
    //接收
    RECEIVE("03"),
    //直属任务的创建
    DIRECT_TASK_CREATE("04"),
    //直属任务的结束
    DIRECT_TASK_END("05"),
    //超时
    OVER_TIME("06"),
    //评价
    EVALUATE("07"),
    //结束
    END("08"),
    //终止
    TERMINATION("09"),
    //提交
    SUBMIT("10"),
    //审查跳过
    REVIEW_SKIPPING("11"),
    //审查通过
    REVIEW_PASS("12"),
    //审查不通过
    REVIEW_NO_PASS("13");

    private String value;

    ProjectEventEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
