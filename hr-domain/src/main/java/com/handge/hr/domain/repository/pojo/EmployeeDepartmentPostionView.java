package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import java.util.Date;

public class EmployeeDepartmentPostionView {
    @Id
    private String name;

    private Date gmtCreate;

    private Date hiredate;

    private Date leavedate;

    private String jobNumber;

    private String positionName;

    private String departmentName;

    private String description;

    private String professionalLevel;

    private String grade;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public Date getLeavedate() {
        return leavedate;
    }

    public void setLeavedate(Date leavedate) {
        this.leavedate = leavedate;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber == null ? null : jobNumber.trim();
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName == null ? null : positionName.trim();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getProfessionalLevel() {
        return professionalLevel;
    }

    public void setProfessionalLevel(String professionalLevel) {
        this.professionalLevel = professionalLevel == null ? null : professionalLevel.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    @Override
    public String toString() {
        return "EmployeeDepartmentPostionView{" +
                "name='" + name + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", hiredate=" + hiredate +
                ", leavedate=" + leavedate +
                ", jobNumber='" + jobNumber + '\'' +
                ", positionName='" + positionName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", description='" + description + '\'' +
                ", professionalLevel='" + professionalLevel + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}