package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name="entity_department")
public class EntityDepartment {
    @Id
    private String id;

    private Date gmtCreate;

    private Date gmtModified;

    private String name;

    private String description;

    private String headerEmployeeId;

    private Integer status;

    private String higherDepartmentId;

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

    public String getHeaderEmployeeId() {
        return headerEmployeeId;
    }

    public void setHeaderEmployeeId(String headerEmployeeId) {
        this.headerEmployeeId = headerEmployeeId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHigherDepartmentId() {
        return higherDepartmentId;
    }

    public void setHigherDepartmentId(String higherDepartmentId) {
        this.higherDepartmentId = higherDepartmentId;
    }

    @Override
    public String toString() {
        return "EntityDepartment{" +
                "id='" + id + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", headerEmployeeId='" + headerEmployeeId + '\'' +
                ", status='" + status + '\'' +
                ", higherDepartmentId='" + higherDepartmentId + '\'' +
                '}';
    }
}