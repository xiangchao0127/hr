package com.handge.hr.domain.entity.manage.web.response.workflow;

import com.handge.hr.common.utils.PageResults;

import java.util.List;

/**
 * create by xc in 2018/08/27
 */
public class TaskDetailsRes {
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
     * 工作类型
     */
    private String workType;
    /**
     * 任务
     */
    private String task;
    /**
     * 部门id
     */
    private String departmentId;
    /**
     * 任务状态
     */
    private String taskStatus;
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
     * 紧急程度
     */
    private String urgency;
    /**
     * 难度
     */
    private String difficuly;
    /**
     * 工作量
     */
    private String workload;
    /**
     * QC
     */
    private String qc;
    /**
     * 任务负责人
     */
    private String receiver;
    /**
     * 任务最小时间
     */
    private String minTime;
    /**
     * 任务最大时间
     */
    private String maxTime;

    private PageResults pageResults;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProjectId() {
        return projectId;
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

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
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

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getDifficuly() {
        return difficuly;
    }

    public void setDifficuly(String difficuly) {
        this.difficuly = difficuly;
    }

    public String getWorkload() {
        return workload;
    }

    public void setWorkload(String workload) {
        this.workload = workload;
    }

    public String getQc() {
        return qc;
    }

    public void setQc(String qc) {
        this.qc = qc;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public PageResults getPageResults() {
        return pageResults;
    }

    public void setPageResults(PageResults pageResults) {
        this.pageResults = pageResults;
    }

    @Override
    public String toString() {
        return "TaskDetailsRes{" +
                "taskId='" + taskId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", parentTaskId='" + parentTaskId + '\'' +
                ", workType='" + workType + '\'' +
                ", task='" + task + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", planStartTime='" + planStartTime + '\'' +
                ", planEndTime='" + planEndTime + '\'' +
                ", actualStartTime='" + actualStartTime + '\'' +
                ", actualEndTime='" + actualEndTime + '\'' +
                ", urgency='" + urgency + '\'' +
                ", difficuly='" + difficuly + '\'' +
                ", workload='" + workload + '\'' +
                ", qc='" + qc + '\'' +
                ", receiver='" + receiver + '\'' +
                ", minTime='" + minTime + '\'' +
                ", maxTime='" + maxTime + '\'' +
                ", pageResults=" + pageResults +
                '}';
    }
}
