package com.handge.hr.domain.entity.manage.web.request.workflow;

import com.handge.hr.domain.entity.base.web.request.PageParam;

import javax.validation.constraints.NotEmpty;

/**
 * create by xc in 2018/08/31
 */
public class ProjectHistorySelectParam extends PageParam {
    /**
     * 项目id
     */
    @NotEmpty
    private String projectId;
    /**
     * 开始时间
     */
    private String searchStartTime;
    /**
     * 结束时间
     */
    private String searchEndTime;

    public String getSearchStartTime() {
        return searchStartTime;
    }

    public void setSearchStartTime(String searchStartTime) {
        this.searchStartTime = searchStartTime;
    }

    public String getSearchEndTime() {
        return searchEndTime;
    }

    public void setSearchEndTime(String searchEndTime) {
        this.searchEndTime = searchEndTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "ProjectHistorySelectParam{" +
                "projectId='" + projectId + '\'' +
                ", searchStartTime='" + searchStartTime + '\'' +
                ", searchEndTime='" + searchEndTime + '\'' +
                '}';
    }
}
