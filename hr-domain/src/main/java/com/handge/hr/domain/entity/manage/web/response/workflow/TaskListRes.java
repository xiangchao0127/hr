package com.handge.hr.domain.entity.manage.web.response.workflow;

/** 任务列表
 * create by xc in 2018/08/27
 */
public class TaskListRes {
    /**
     * 项目id
     */
    private String id;
    /**
     * 所属项目
     */
    private String projectName;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 负责人
     */
    private String chargeName;
    /**
     * 任务级别
     */
    private String taskLevel;
    /**
     * 任务周期
     */
    private String schedule;
    /**
     * 完成度
     */
    private String completion;
    /**
     * 状态
     */
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public String getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(String taskLevel) {
        this.taskLevel = taskLevel;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskListRes{" +
                "id='" + id + '\'' +
                ", projectName='" + projectName + '\'' +
                ", taskName='" + taskName + '\'' +
                ", chargeName='" + chargeName + '\'' +
                ", taskLevel='" + taskLevel + '\'' +
                ", schedule='" + schedule + '\'' +
                ", completion='" + completion + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
