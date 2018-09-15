package com.handge.hr.domain.entity.manage.web.request.archive;

public class DepartmentParam {

    private String id;

    private String name;

    private String description;

    private String headerEmployeeId;

    private Integer status;

    private String higherDepartmentId;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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
        return "DepartmentParam{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", headerEmployeeId='" + headerEmployeeId + '\'' +
                ", status='" + status + '\'' +
                ", higherDepartmentId='" + higherDepartmentId + '\'' +
                '}';
    }
}
