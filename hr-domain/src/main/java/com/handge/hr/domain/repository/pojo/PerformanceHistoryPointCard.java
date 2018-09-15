package com.handge.hr.domain.repository.pojo;

import com.handge.hr.domain.handler.JsonTypeHandler;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name = "performance_history_point_card")
public class PerformanceHistoryPointCard {
    @Id
    private String id;

    private Date gmtCreate;

    private Date gmtModified;

    private String employeeId;

    private Date usedDate;

    @ColumnType(typeHandler = JsonTypeHandler.class)
    private Object content;

    private String historyViewCardId;

    private String origin;

    private String originId;

    private String pointCardId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId == null ? null : employeeId.trim();
    }

    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getHistoryViewCardId() {
        return historyViewCardId;
    }

    public void setHistoryViewCardId(String historyViewCardId) {
        this.historyViewCardId = historyViewCardId == null ? null : historyViewCardId.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId == null ? null : originId.trim();
    }

    public String getPointCardId() {
        return pointCardId;
    }

    public void setPointCardId(String pointCardId) {
        this.pointCardId = pointCardId == null ? null : pointCardId.trim();
    }
}