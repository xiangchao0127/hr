package com.handge.hr.common.enumeration.manage;

/**
 * Created by MaJianfu on 2018/6/25.
 */
public enum TaskStatusEnum {
    //未接收
    UNRECEIVED("01"),
    //进行中
    MAKING("02"),
    // 审查中
    QC("03"),
    //待评价
    EVALUATE("04"),
    //已完成
    COMPLETED("05"),
    //已终止
    TERMINATION("06");


    private String value;

    TaskStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static TaskStatusEnum getEnumByValue(String value){
        if(value == null){
            return null;
        }
        TaskStatusEnum[] values = TaskStatusEnum.values();
        for(TaskStatusEnum taskStatusEnum : values){
            if(value.equals(taskStatusEnum.getValue())){
                return taskStatusEnum;
            }
        }
        return null;
    }


}
