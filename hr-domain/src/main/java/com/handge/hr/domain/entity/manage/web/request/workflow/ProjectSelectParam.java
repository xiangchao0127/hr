package com.handge.hr.domain.entity.manage.web.request.workflow;

import com.handge.hr.domain.entity.base.web.request.PageParam;

/** 项目列表请求参数
 * CREATE by xc in 2018/08/21
 */
public class ProjectSelectParam extends PageParam {
    /**
     * 项目编号
     */
    private String projectNumber;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 负责人
     */
    private String createName;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 项目状态
     */
    private String projectStatus;

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

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    @Override
    public String toString() {
        return "ProjectSelectParam{" +
                "projectNumber='" + projectNumber + '\'' +
                ", projectName='" + projectName + '\'' +
                ", createName='" + createName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", projectStatus='" + projectStatus + '\'' +
                '}';
    }
}
