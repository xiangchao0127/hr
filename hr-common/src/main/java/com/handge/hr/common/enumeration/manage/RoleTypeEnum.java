package com.handge.hr.common.enumeration.manage;

/**
 * @author Liujuhao
 * @date 2018/9/6.
 */
public enum  RoleTypeEnum {

    //公司领导
    COMPANY_LEADER("01"),
    //部门领导
    DEPARTMENT_LEADER("02"),
    //项目领导
    PROJECT_LEADER("03"),
    //HR
    HR_STAFF("04"),
    //系统管理员
    ADMINISTRATOR("99"),
    //普通员工
    COMMON_STAFF("00");

    private String code;

    RoleTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}