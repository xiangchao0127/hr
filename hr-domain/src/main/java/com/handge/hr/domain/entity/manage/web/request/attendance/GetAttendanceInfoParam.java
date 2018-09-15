package com.handge.hr.domain.entity.manage.web.request.attendance;

/**
 * @author liuqian
 * @date 2018/8/31
 * @Description:考勤信息
 */
public class GetAttendanceInfoParam {
    /**
     * 工号
     */
    private String number;
    /**
     * 月份（形如：2018-08）
     */
    private String month;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
