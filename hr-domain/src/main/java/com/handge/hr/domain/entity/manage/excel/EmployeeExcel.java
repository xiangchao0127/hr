package com.handge.hr.domain.entity.manage.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by MaJianfu on 2018/7/26.
 */
public class EmployeeExcel implements Serializable {

    @Excel(name ="编号")
    private String jobNumber;
    @Excel(name ="姓名")
    private String name;
    @Excel(name ="部门")
    private String department;
    @Excel(name ="职级")
    private String professionalLevel;
    @Excel(name = "岗位")
    private String position;
    @Excel(name = "入职时间", format = "yyyy-MM-dd")
    private Date hiredate;
    @Excel(name = "就职状态")
    private String jobStatus;
    @Excel(name = "出生日期", format = "yyyy-MM-dd")
    private Date birthday;
    @Excel(name = "性别")
    private String gender;
    @Excel(name = "民族")
    private String nationality;
    @Excel(name = "籍贯")
    private String nativePlace;
    @Excel(name = "身份证号")
    private String identityCard;
    @Excel(name = "现居住址")
    private String address;
    @Excel(name = "婚姻状况")
    private String maritalStatus;
    @Excel(name = "子女情况")
    private String childrenStatus;
    @Excel(name = "毕业学校")
    private String graduateFrom;
    @Excel(name = "专业")
    private String professional;
    @Excel(name = "学历")
    private String education;
    @Excel(name = "政治面貌")
    private String politicalStatus;
    @Excel(name = "手机")
    private String mobile;
    @Excel(name = "备注")
    private String remark;

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

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
