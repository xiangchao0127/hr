package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="workflow_qc")
public class WorkflowQc {
    @Id
    private String id;

    private String projectTypeId;

    private String departmentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(String projectTypeId) {
        this.projectTypeId = projectTypeId == null ? null : projectTypeId.trim();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }
}