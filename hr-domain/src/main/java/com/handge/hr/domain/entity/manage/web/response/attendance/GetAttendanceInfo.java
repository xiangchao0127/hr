package com.handge.hr.domain.entity.manage.web.response.attendance;

import com.handge.hr.common.enumeration.manage.AttendanceStatusEnum;

/**
 * @author liuqian
 * @date 2018/8/31
 * @Description:考勤信息
 */
public class GetAttendanceInfo {
    /**
     * 考勤日期
     */
    String attendanceDate;
    /**
     * 考勤状态--早
     */
    AttendanceStatusEnum earlyStatus;
    /**
     * 考勤状态--晚
     */
    AttendanceStatusEnum eveningStatus;
    /**
     * 第一次打卡时间
     */
    String firstPunchCardTime;
    /**
     * 最后一次打卡时间
     */
    String lastPunchCardTime;
    /**
     * 加班时长
     */
    String overTime;

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public AttendanceStatusEnum getEarlyStatus() {
        return earlyStatus;
    }

    public void setEarlyStatus(AttendanceStatusEnum earlyStatus) {
        this.earlyStatus = earlyStatus;
    }

    public AttendanceStatusEnum getEveningStatus() {
        return eveningStatus;
    }

    public void setEveningStatus(AttendanceStatusEnum eveningStatus) {
        this.eveningStatus = eveningStatus;
    }

    public String getFirstPunchCardTime() {
        return firstPunchCardTime;
    }

    public void setFirstPunchCardTime(String firstPunchCardTime) {
        this.firstPunchCardTime = firstPunchCardTime;
    }

    public String getLastPunchCardTime() {
        return lastPunchCardTime;
    }

    public void setLastPunchCardTime(String lastPunchCardTime) {
        this.lastPunchCardTime = lastPunchCardTime;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    @Override
    public String toString() {
        return "GetAttendanceInfo{" +
                "attendanceDate='" + attendanceDate + '\'' +
                ", earlyStatus=" + earlyStatus +
                ", eveningStatus=" + eveningStatus +
                ", firstPunchCardTime='" + firstPunchCardTime + '\'' +
                ", lastPunchCardTime='" + lastPunchCardTime + '\'' +
                ", overTime='" + overTime + '\'' +
                '}';
    }
}
