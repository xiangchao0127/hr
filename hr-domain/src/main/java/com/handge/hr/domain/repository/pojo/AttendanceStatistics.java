package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

public class AttendanceStatistics {
    @Id
    private String id;

    private String statisticsDate;

    private BigDecimal noArrivalRate;

    private BigDecimal lateRate;

    private Integer lateTimesCount;

    private BigDecimal leaveEarlyRate;

    private Integer leaveEarlyTimesCount;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer latePersonCount;

    private Integer leaveEarlyPersonCount;

    private BigDecimal noArrivalTimesCount;

    private Integer noArrivalPersonCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(String statisticsDate) {
        this.statisticsDate = statisticsDate == null ? null : statisticsDate.trim();
    }

    public BigDecimal getNoArrivalRate() {
        return noArrivalRate;
    }

    public void setNoArrivalRate(BigDecimal noArrivalRate) {
        this.noArrivalRate = noArrivalRate;
    }

    public BigDecimal getLateRate() {
        return lateRate;
    }

    public void setLateRate(BigDecimal lateRate) {
        this.lateRate = lateRate;
    }

    public Integer getLateTimesCount() {
        return lateTimesCount;
    }

    public void setLateTimesCount(Integer lateTimesCount) {
        this.lateTimesCount = lateTimesCount;
    }

    public BigDecimal getLeaveEarlyRate() {
        return leaveEarlyRate;
    }

    public void setLeaveEarlyRate(BigDecimal leaveEarlyRate) {
        this.leaveEarlyRate = leaveEarlyRate;
    }

    public Integer getLeaveEarlyTimesCount() {
        return leaveEarlyTimesCount;
    }

    public void setLeaveEarlyTimesCount(Integer leaveEarlyTimesCount) {
        this.leaveEarlyTimesCount = leaveEarlyTimesCount;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getLatePersonCount() {
        return latePersonCount;
    }

    public void setLatePersonCount(Integer latePersonCount) {
        this.latePersonCount = latePersonCount;
    }

    public Integer getLeaveEarlyPersonCount() {
        return leaveEarlyPersonCount;
    }

    public void setLeaveEarlyPersonCount(Integer leaveEarlyPersonCount) {
        this.leaveEarlyPersonCount = leaveEarlyPersonCount;
    }

    public BigDecimal getNoArrivalTimesCount() {
        return noArrivalTimesCount;
    }

    public void setNoArrivalTimesCount(BigDecimal noArrivalTimesCount) {
        this.noArrivalTimesCount = noArrivalTimesCount;
    }

    public Integer getNoArrivalPersonCount() {
        return noArrivalPersonCount;
    }

    public void setNoArrivalPersonCount(Integer noArrivalPersonCount) {
        this.noArrivalPersonCount = noArrivalPersonCount;
    }
}