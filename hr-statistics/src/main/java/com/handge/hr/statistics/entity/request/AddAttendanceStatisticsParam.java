package com.handge.hr.statistics.entity.request;

/**
 * @author liuqian
 * @date 2018/9/5
 * @Description:
 */
public class AddAttendanceStatisticsParam {
    /**
     * 月份（形如:"2018-09"）
     */
    String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "AddAttendanceStatisticsParam{" +
                "month='" + month + '\'' +
                '}';
    }
}
