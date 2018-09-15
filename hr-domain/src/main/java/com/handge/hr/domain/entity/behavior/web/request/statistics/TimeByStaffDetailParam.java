package com.handge.hr.domain.entity.behavior.web.request.statistics;


import com.handge.hr.domain.entity.base.web.request.TimeParam;

import java.io.Serializable;

/**
 * @author liuqian
 */
public class TimeByStaffDetailParam extends TimeParam implements Serializable {
    /**
     * 部门
     */
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "TimeByStaffDetailParam{" +
                "department='" + department + '\'' +
                '}';
    }

}
