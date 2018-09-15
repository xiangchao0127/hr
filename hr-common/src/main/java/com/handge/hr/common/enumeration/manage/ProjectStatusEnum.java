package com.handge.hr.common.enumeration.manage;

/**
 * Created by MaJianfu on 2018/6/25.
 */
public enum ProjectStatusEnum {
    //未接收
    UNRECEIVED("01"),
    //进行中
    MAKING("02"),
    //已完成
    COMPLETED("03"),
    //已终止
    TERMINATION("04");

    private String value;

    ProjectStatusEnum(String value) {
        this.value = value;
    }

    public static ProjectStatusEnum getEnumByValue(String value){
        if(value == null){
            return null;
        }
        ProjectStatusEnum[] values = ProjectStatusEnum.values();
        for(ProjectStatusEnum projectStatusEnum : values){
            if(value.equals(projectStatusEnum.getValue())){
                return projectStatusEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
