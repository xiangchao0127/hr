package com.handge.hr.common.enumeration.behavior;


import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;

/**
 * Created by DaLu Guo on 2018/6/13.
 */
public enum EmployeeStatusEnum {
    // 20：正式员工，21：试用期，22：实习期，23：考察期，24：离职，25：外派

    FORMAL("20", "正式员工"),
    PROBATION("21", "试用期"),
    PRACTICE("22", "实习期"),
    INSPECTION("23","考察期"),
    QUIT("24", "离职"),
    EXPATRIATE("25","外派");

    private String status;
    private String desc;

    EmployeeStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    /**
     * 根据状态查询对应的描述
     *
     * @param status
     * @return
     */
    public static String getDescByStatus(String status) {
        EmployeeStatusEnum[] values = EmployeeStatusEnum.values();
        for (EmployeeStatusEnum employeeStatusEnum : values) {
            if (employeeStatusEnum.getStatus().equals(status)) {
                return employeeStatusEnum.getDesc();
            }
        }
        throw new UnifiedException("状态", ExceptionWrapperEnum.Parameter_Enum_NOT_Match);
    }

    public static String getStatusByDesc(String desc) {
        EmployeeStatusEnum[] values = EmployeeStatusEnum.values();
        for (EmployeeStatusEnum employeeStatusEnum : values) {
            if (employeeStatusEnum.getDesc().equals(desc)) {
                return employeeStatusEnum.getStatus();
            }
        }
        throw new UnifiedException("状态", ExceptionWrapperEnum.Parameter_Enum_NOT_Match);
    }

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
