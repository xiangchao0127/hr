package com.handge.hr.domain.entity.manage.web.response.workflow;

/**
 * create by xc in 2018/08/21
 */
public class TaskGanntChart {
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 负责部门
     */
    private String taskChargeDepartment;
    /**
     * 负责人
     */
    private String chargeName;
    /**
     * 任务级别
     */
    private String taskLevel;
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
     * 计划开始坐标点
     */
    private String planStartPoint;
    /**
     * 实际开始坐标点
     */
    private String actualStartPoint;
    /**
     * 计划结束时间到开始坐标的距离
     */
    private String planEndDistance;
    /**
     * 实际结束时间到实际开始坐标的距离
     */
    private String actualEndDistance;


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskChargeDepartment() {
        return taskChargeDepartment;
    }

    public void setTaskChargeDepartment(String taskChargeDepartment) {
        this.taskChargeDepartment = taskChargeDepartment;
    }

    public String getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(String taskLevel) {
        this.taskLevel = taskLevel;
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

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }


    public String getActualStartPoint() {
        return actualStartPoint;
    }

    public void setActualStartPoint(String actualStartPoint) {
        this.actualStartPoint = actualStartPoint;
    }

    public String getPlanEndDistance() {
        return planEndDistance;
    }

    public void setPlanEndDistance(String planEndDistance) {
        this.planEndDistance = planEndDistance;
    }

    public String getActualEndDistance() {
        return actualEndDistance;
    }

    public void setActualEndDistance(String actualEndDistance) {
        this.actualEndDistance = actualEndDistance;
    }

    public String getPlanStartPoint() {
        return planStartPoint;
    }

    public void setPlanStartPoint(String planStartPoint) {
        this.planStartPoint = planStartPoint;
    }

    @Override
    public String toString() {
        return "TaskGanntChart{" +
                "taskName='" + taskName + '\'' +
                ", taskChargeDepartment='" + taskChargeDepartment + '\'' +
                ", chargeName='" + chargeName + '\'' +
                ", taskLevel='" + taskLevel + '\'' +
                ", planStartTime='" + planStartTime + '\'' +
                ", planEndTime='" + planEndTime + '\'' +
                ", actualStartTime='" + actualStartTime + '\'' +
                ", actualEndTime='" + actualEndTime + '\'' +
                ", planStartPoint='" + planStartPoint + '\'' +
                ", actualStartPoint='" + actualStartPoint + '\'' +
                ", planEndDistance='" + planEndDistance + '\'' +
                ", actualEndDistance='" + actualEndDistance + '\'' +
                '}';
    }
}
