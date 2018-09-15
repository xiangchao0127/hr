package com.handge.hr.domain.entity.manage.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MaJianfu on 2018/8/10.
 */
public class AttendanceFlowRecordsExcel implements Serializable {

    @Excel(name ="考勤机用户id")
    private String uid;
    @Excel(name ="打卡时间")
    private Date dkDate;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getDkDate() {
        return dkDate;
    }

    public void setDkDate(Date dkDate) {
        this.dkDate = dkDate;
    }

}
