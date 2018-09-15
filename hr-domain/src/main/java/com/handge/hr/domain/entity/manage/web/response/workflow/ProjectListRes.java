package com.handge.hr.domain.entity.manage.web.response.workflow;

/**
 * create by xc in 2018/08/21
 */
public class ProjectListRes {
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 序号
      */
    private String  orderNumber;
    /**
     * 项目编号
      */
    private String  projectNumber;
    /**
     * 项目名称
     */
    private String  projectName;
    /**
     * 计划开始时间
     */
    private String  projectStartTime;
    /**
     * 计划结束时间
     */
    private String  projectEndTime;
    /**
     * 负责人
     */
    private String  projectPrincipal;
    /**
     * 完成度
     */
    private String  projectCompletePercent;
    /**
     * 项目状态
     */
    private String  projectStatus;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectStartTime() {
        return projectStartTime;
    }

    public void setProjectStartTime(String projectStartTime) {
        this.projectStartTime = projectStartTime;
    }

    public String getProjectEndTime() {
        return projectEndTime;
    }

    public void setProjectEndTime(String projectEndTime) {
        this.projectEndTime = projectEndTime;
    }

    public String getProjectPrincipal() {
        return projectPrincipal;
    }

    public void setProjectPrincipal(String projectPrincipal) {
        this.projectPrincipal = projectPrincipal;
    }

    public String getProjectCompletePercent() {
        return projectCompletePercent;
    }

    public void setProjectCompletePercent(String projectCompletePercent) {
        this.projectCompletePercent = projectCompletePercent;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }
}
