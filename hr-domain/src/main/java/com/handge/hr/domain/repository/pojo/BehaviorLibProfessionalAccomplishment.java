package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "behavior_lib_professional_accomplishment")
public class BehaviorLibProfessionalAccomplishment {

    @Id
    private Integer id;

    private String staticIp;

    private String time;

    private String loyalty;

    private String workingAttitude;

    private String complianceDiscipline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStaticIp() {
        return staticIp;
    }

    public void setStaticIp(String staticIp) {
        this.staticIp = staticIp == null ? null : staticIp.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(String loyalty) {
        this.loyalty = loyalty == null ? null : loyalty.trim();
    }

    public String getWorkingAttitude() {
        return workingAttitude;
    }

    public void setWorkingAttitude(String workingAttitude) {
        this.workingAttitude = workingAttitude == null ? null : workingAttitude.trim();
    }

    public String getComplianceDiscipline() {
        return complianceDiscipline;
    }

    public void setComplianceDiscipline(String complianceDiscipline) {
        this.complianceDiscipline = complianceDiscipline == null ? null : complianceDiscipline.trim();
    }

    @Override
    public String toString() {
        return "BehaviorLibProfessionalAccomplishment{" +
                "id=" + id +
                ", staticIp='" + staticIp + '\'' +
                ", time='" + time + '\'' +
                ", loyalty='" + loyalty + '\'' +
                ", workingAttitude='" + workingAttitude + '\'' +
                ", complianceDiscipline='" + complianceDiscipline + '\'' +
                '}';
    }
}