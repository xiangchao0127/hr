package com.handge.hr.domain.entity.behavior.web.request.monitor;


import com.handge.hr.domain.entity.base.web.request.PageParam;

import java.io.Serializable;

/**
 * Created by DaLu Guo on 2018/6/1.
 */
public class NonWorkingDepartmentDetailParam extends PageParam implements Serializable {

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
        return "NonWorkingDepartmentDetailParam{" +
                "department='" + department + '\'' +
                '}';
    }
}
