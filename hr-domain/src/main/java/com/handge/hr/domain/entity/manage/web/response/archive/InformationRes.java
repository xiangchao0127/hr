package com.handge.hr.domain.entity.manage.web.response.archive;



/**
 * Created by MaJianfu on 2018/7/31.
 */
public class InformationRes {
    //工号
    private String jobNumber;
    //姓名
    private String name;
    //部门
    private String department;
    //职级
    private String professionalLevel;
    //职务
    private String position;
    //入职时间
    private String hireDate;
    //工龄
    private String seniority;
    //就职状态
    private String jobStatus;
    //出生日期
    private String birthday;
    //性别
    private String gender;
    //民族
    private String nationality;
    //籍贯
    private String nativePlace;
    //身份证号
    private String identityCard;
    //现居住址
    private String address;
    //婚姻状况
    private String maritalStatus;
    //子女情况
    private String childrenStatus;
    //毕业学校
    private String graduateFrom;
    //专业
    private String professional;
    //学历
    private String education;
    //政治面貌
    private String politicalStatus;
    //手机
    private String mobile;
    //备注
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProfessionalLevel() {
        return professionalLevel;
    }

    public void setProfessionalLevel(String professionalLevel) {
        this.professionalLevel = professionalLevel;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSeniority() {
        return seniority;
    }

    public void setSeniority(String seniority) {
        this.seniority = seniority;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "InformationRes{" +
                "jobNumber='" + jobNumber + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", professionalLevel='" + professionalLevel + '\'' +
                ", position='" + position + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", seniority='" + seniority + '\'' +
                ", JOB_STATUS='" + jobStatus + '\'' +
                ", birthday='" + birthday + '\'' +
                ", GENDER='" + gender + '\'' +
                ", nationality='" + nationality + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", address='" + address + '\'' +
                ", MARITAL_STATUS='" + maritalStatus + '\'' +
                ", CHILDREN_STATUS='" + childrenStatus + '\'' +
                ", graduateFrom='" + graduateFrom + '\'' +
                ", professional='" + professional + '\'' +
                ", EDUCATION='" + education + '\'' +
                ", POLITICAL_STATUS='" + politicalStatus + '\'' +
                ", mobile='" + mobile + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
