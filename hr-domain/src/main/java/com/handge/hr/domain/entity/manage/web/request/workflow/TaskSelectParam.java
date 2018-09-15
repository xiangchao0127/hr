package com.handge.hr.domain.entity.manage.web.request.workflow;

import com.handge.hr.domain.entity.base.web.request.PageParam;

/**
 * create by xc in 2018/08/27
 */
public class TaskSelectParam extends PageParam {
    /**
     * 任务搜索
     */
    private String projectName;
    /**
     * 任务周期
     */
    private String schedule;
    /**
     * 任务级别
     */
    private String taskLevel;
    /**
     * 紧急程度
     */
    private String urgency;
    /**
     * 项目状态
     */
    private String status;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(String taskLevel) {
        this.taskLevel = taskLevel;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskSelectParam{" +
                "projectName='" + projectName + '\'' +
                ", schedule='" + schedule + '\'' +
                ", taskLevel='" + taskLevel + '\'' +
                ", urgency='" + urgency + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
