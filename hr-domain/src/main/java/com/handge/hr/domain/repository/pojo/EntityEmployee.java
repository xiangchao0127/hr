package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name="entity_employee")
public class EntityEmployee implements Serializable{
    @Id
    private String id;

    private Date gmtCreate;

    private Date gmtModified;

    private String name;

    private Date hiredate;

    private Date leavedate;

    private String education;

    private String gender;

    private String jobNumber;

    private String birthday;

    private String mobile;

    private String jobStatus;

    private String nationality;

    private String nativePlace;

    private String identityCard;

    private String address;

    private String degree;

    private String politicalStatus;

    private String emergencyName;

    private String emergencyRelation;

    private String emergencyPhone;

    private String talentAbility;

    private String remark;

    private String departmentId;

    private String jobProfessionalLevelId;

    private String salaryId;

    private String jobTitleId;

    private String jobPostId;

    private String jobPositionId;

    private String maritalStatus;

    private String childrenStatus;

    private String graduateFrom;

    private String professional;

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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getEmergencyName() {
        return emergencyName;
    }

    public void setEmergencyName(String emergencyName) {
        this.emergencyName = emergencyName;
    }

    public String getEmergencyRelation() {
        return emergencyRelation;
    }

    public void setEmergencyRelation(String emergencyRelation) {
        this.emergencyRelation = emergencyRelation;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public String getTalentAbility() {
        return talentAbility;
    }

    public void setTalentAbility(String talentAbility) {
        this.talentAbility = talentAbility;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobProfessionalLevelId() {
        return jobProfessionalLevelId;
    }

    public void setJobProfessionalLevelId(String jobProfessionalLevelId) {
        this.jobProfessionalLevelId = jobProfessionalLevelId;
    }

    public String getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId;
    }

    public String getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(String jobPostId) {
        this.jobPostId = jobPostId;
    }

    public String getJobPositionId() {
        return jobPositionId;
    }

    public void setJobPositionId(String jobPositionId) {
        this.jobPositionId = jobPositionId;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getChildrenStatus() {
        return childrenStatus;
    }

    public void setChildrenStatus(String childrenStatus) {
        this.childrenStatus = childrenStatus;
    }

    public String getGraduateFrom() {
        return graduateFrom;
    }

    public void setGraduateFrom(String graduateFrom) {
        this.graduateFrom = graduateFrom;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    @Override
    public String toString() {
        return "EntityEmployee{" +
                "id='" + id + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", name='" + name + '\'' +
                ", hiredate=" + hiredate +
                ", leavedate=" + leavedate +
                ", EDUCATION=" + education +
                ", GENDER=" + gender +
                ", jobNumber='" + jobNumber + '\'' +
                ", birthday='" + birthday + '\'' +
                ", mobile='" + mobile + '\'' +
                ", JOB_STATUS=" + jobStatus +
                ", nationality='" + nationality + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", address='" + address + '\'' +
                ", degree='" + degree + '\'' +
                ", POLITICAL_STATUS=" + politicalStatus +
                ", emergencyName='" + emergencyName + '\'' +
                ", emergencyRelation='" + emergencyRelation + '\'' +
                ", emergencyPhone='" + emergencyPhone + '\'' +
                ", talentAbility='" + talentAbility + '\'' +
                ", remark='" + remark + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", jobProfessionalLevelId='" + jobProfessionalLevelId + '\'' +
                ", salaryId='" + salaryId + '\'' +
                ", jobTitleId='" + jobTitleId + '\'' +
                ", jobPostId='" + jobPostId + '\'' +
                ", jobPositionId='" + jobPositionId + '\'' +
                ", MARITAL_STATUS=" + maritalStatus +
                ", CHILDREN_STATUS=" + childrenStatus +
                ", graduateFrom='" + graduateFrom + '\'' +
                ", professional='" + professional + '\'' +
                '}';
    }
}