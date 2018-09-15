package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import java.util.Date;

public class AttendanceFlowRecord {
    @Id
    private String id;

    private String uid;

    private Date dkDate;

    private String jobNumber;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Date getDkDate() {
        return dkDate;
    }

    public void setDkDate(Date dkDate) {
        this.dkDate = dkDate;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber == null ? null : jobNumber.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}