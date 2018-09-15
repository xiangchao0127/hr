package com.handge.hr.common.enumeration.manage;

/**
 * Created by DaLu Guo on 2018/8/23.
 */
public enum EvaluateOriginTypeEnum {
    //任务
    TASK("02"),
    //任务审查
    TASK_QC("03"),
    //项目
    PROJECT("01");

    private String code;

    EvaluateOriginTypeEnum(String name) {
        this.code = name;
    }
    public String getCode() {
        return code;
    }
}
