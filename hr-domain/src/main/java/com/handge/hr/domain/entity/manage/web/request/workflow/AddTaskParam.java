package com.handge.hr.domain.entity.manage.web.request.workflow;


/**
 * Created by MaJianfu on 2018/8/16.
 */
public class AddTaskParam {
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 上级任务id
     */
    private String parentTaskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务内容
     */
    private String content;
    /**
     * 任务类型
     */
    private String type;
    /**
     * 部门id
     */
    private String departmentId;
    /**
     * 计划开始时间
     */
    private String planStartTime;
    /**
     * 计划结束时间
     */
    private String planEndTime;
    /**
     * 难度
     */
    private String difficult;
    /**
     * 紧急程度
     */
    private String urgency;
    /**
     * 工作量
     */
    private String workload;

    /**
     * 任务负责人
     */
    private String receiver;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getWorkload() {
        return workload;
    }

    public void setWorkload(String workload) {
        this.workload = workload;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "AddTaskParam{" +
                "taskId='" + taskId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", parentTaskId='" + parentTaskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", planStartTime='" + planStartTime + '\'' +
                ", planEndTime='" + planEndTime + '\'' +
                ", difficult='" + difficult + '\'' +
                ", urgency='" + urgency + '\'' +
                ", workload='" + workload + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
