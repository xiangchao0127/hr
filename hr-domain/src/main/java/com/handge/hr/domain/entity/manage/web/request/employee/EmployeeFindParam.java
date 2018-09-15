package com.handge.hr.domain.entity.manage.web.request.employee;

public class EmployeeFindParam {
    private String name;
    private String birthday;
    private String jobNumber;
    private String identityCard;
    private String departmentName;
    private String jobPostion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobPostion() {
        return jobPostion;
    }

    public void setJobPostion(String jobPostion) {
        this.jobPostion = jobPostion;
    }

    @Override
    public String toString() {
        return "EmployeeFindParam{" +
                "name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", jobPostion='" + jobPostion + '\'' +
                '}';
    }
}
