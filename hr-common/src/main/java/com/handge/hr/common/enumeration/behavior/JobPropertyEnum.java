package com.handge.hr.common.enumeration.behavior;

public enum JobPropertyEnum {
    //工作相关
    WORK_RELATED("1","工作相关"),
    //工作无关
    WORK_NO_RELATED("0","工作无关"),
    //不确定
    UNCERTAIN("2","不确定");

    private String code;
    private String desc;

    JobPropertyEnum(String code,String desc) {
        this.code = code;
        this.desc = desc;
    }



    public String getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }
}
