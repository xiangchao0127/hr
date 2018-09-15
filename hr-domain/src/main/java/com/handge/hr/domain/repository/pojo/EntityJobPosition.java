package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name="entity_job_position")
public class EntityJobPosition {
    @Id
    private String id;

    private Date gmtCreate;

    private Date gmtModified;

    private String name;

    private String description;

    private Integer status;

    private String higherPositionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHigherPositionId() {
        return higherPositionId;
    }

    public void setHigherPositionId(String higherPositionId) {
        this.higherPositionId = higherPositionId;
    }

    @Override
    public String toString() {
        return "EntityJobPosition{" +
                "id='" + id + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", higherPositionId='" + higherPositionId + '\'' +
                '}';
    }
}