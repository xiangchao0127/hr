package com.handge.hr.domain.entity.manage.web.response.workflow;

import com.handge.hr.common.utils.PageResults;

import java.util.List;

/**
 * create by xc in 2018/08/21
 */
public class ProjectDetailsRes {
    /**
     * 项目id
     */
    private String id;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目类型
     */
    private String type;
    /**
     * 项目描述
     */
    private String description;
    /**
     * 项目负责人
     */
    private String principal;
    /**
     * 项目状态
     */
    private String status;
    /**
     * 项目编号
     */
    private String number;
    /**
     * 计划开始时间
     */
    private String planStartTime;
    /**
     * 计划结束时间
     */
    private String planEndTime;
    /**
     * 实际开始时间
     */
    private String actualStartTime;
    /**
     * 实际结束时间
     */
    private String actualEndTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 任务最小时间
     */
    private String minTime;
    /**
     * 任务最大时间
     */
    private String maxTime;
    /**
     * 任务甘特图
     */
    private PageResults taskGanntCharts;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public String getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(String actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public String getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(String actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMinTime() {
        return minTime;
    }

    public void setMinTime(String minTime) {
        this.minTime = minTime;
    }

    public String getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    public PageResults getTaskGanntCharts() {
        return taskGanntCharts;
    }

    public void setTaskGanntCharts(PageResults taskGanntCharts) {
        this.taskGanntCharts = taskGanntCharts;
    }

    @Override
    public String toString() {
        return "ProjectDetailsRes{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", principal='" + principal + '\'' +
                ", status='" + status + '\'' +
                ", number='" + number + '\'' +
                ", planStartTime='" + planStartTime + '\'' +
                ", planEndTime='" + planEndTime + '\'' +
                ", actualStartTime='" + actualStartTime + '\'' +
                ", actualEndTime='" + actualEndTime + '\'' +
                ", remark='" + remark + '\'' +
                ", minTime='" + minTime + '\'' +
                ", maxTime='" + maxTime + '\'' +
                ", taskGanntCharts=" + taskGanntCharts +
                '}';
    }
}
