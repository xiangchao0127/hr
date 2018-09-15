package com.handge.hr.domain.entity.manage.web.response.archive;


/**
 * Created by MaJianfu on 2018/8/3.
 */
public class PostRes {

    //职务名称
    private String name;
    //职务职能
    private String description;
    //备注
    private String remark;
    //状态
    private String status;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
