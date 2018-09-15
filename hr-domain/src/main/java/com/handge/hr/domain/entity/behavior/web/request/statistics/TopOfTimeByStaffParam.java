package com.handge.hr.domain.entity.behavior.web.request.statistics;


import com.handge.hr.domain.entity.base.web.request.TimeParam;

import java.io.Serializable;

/**
 * @author liuqian
 */
public class TopOfTimeByStaffParam extends TimeParam implements Serializable {

    /**
     * 排名数量（1,2,3,4,5...）
     */
    private int n = 5;

    /**
     * 部门
     */
    private String department;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "TopOfTimeByStaffParam{" +
                "n=" + n +
                ", department='" + department + '\'' +
                '}';
    }
}
