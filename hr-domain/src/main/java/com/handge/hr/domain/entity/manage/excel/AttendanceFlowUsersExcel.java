package com.handge.hr.domain.entity.manage.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * Created by MaJianfu on 2018/8/10.
 */
public class AttendanceFlowUsersExcel implements Serializable {

    @Excel(name ="考勤机用户id")
    private String uid;
    @Excel(name ="姓名")
    private String name;
    @Excel(name ="工号")
    private String jobNumber;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
}
