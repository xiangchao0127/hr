package com.handge.hr.domain.entity.manage.web.request.workflow;

import javax.validation.constraints.NotEmpty;

/**
 * create by xc in 2018/08/31
 */
public class TaskHistorySelectParam {
    /**
     * 任务id
     */
    @NotEmpty
    private String taskId;
    /**
     * 开始时间
     */
    private String searchStartTime;
    /**
     * 结束时间
     */
    private String searchEndTime;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

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

    @Override
    public String toString() {
        return "TaskHistorySelectParam{" +
                "taskId='" + taskId + '\'' +
                ", searchStartTime='" + searchStartTime + '\'' +
                ", searchEndTime='" + searchEndTime + '\'' +
                '}';
    }
}
