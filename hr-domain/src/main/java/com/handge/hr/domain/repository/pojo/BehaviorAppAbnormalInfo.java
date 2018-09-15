package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table(name="behavior_app_abnormal_info")
public class BehaviorAppAbnormalInfo {
    @Id
    private Integer id;

    private String number;

    private String localip;

    private String appname;

    private String appclass;

    private String appprotocol;

    private String targetip;

    private Date accesstime;

    private String upflow;

    private String downflow;

    private String detail;

    private String typ;

    private String lev;

    private String isProcessed;

    private Date createAt;

    private String attr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getLocalip() {
        return localip;
    }

    public void setLocalip(String localip) {
        this.localip = localip == null ? null : localip.trim();
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname == null ? null : appname.trim();
    }

    public String getAppclass() {
        return appclass;
    }

    public void setAppclass(String appclass) {
        this.appclass = appclass == null ? null : appclass.trim();
    }

    public String getAppprotocol() {
        return appprotocol;
    }

    public void setAppprotocol(String appprotocol) {
        this.appprotocol = appprotocol == null ? null : appprotocol.trim();
    }

    public String getTargetip() {
        return targetip;
    }

    public void setTargetip(String targetip) {
        this.targetip = targetip == null ? null : targetip.trim();
    }

    public Date getAccesstime() {
        return accesstime;
    }

    public void setAccesstime(Date accesstime) {
        this.accesstime = accesstime;
    }

    public String getUpflow() {
        return upflow;
    }

    public void setUpflow(String upflow) {
        this.upflow = upflow == null ? null : upflow.trim();
    }

    public String getDownflow() {
        return downflow;
    }

    public void setDownflow(String downflow) {
        this.downflow = downflow == null ? null : downflow.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ == null ? null : typ.trim();
    }

    public String getLev() {
        return lev;
    }

    public void setLev(String lev) {
        this.lev = lev == null ? null : lev.trim();
    }

    public String getIsProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(String isProcessed) {
        this.isProcessed = isProcessed == null ? null : isProcessed.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr == null ? null : attr.trim();
    }
}