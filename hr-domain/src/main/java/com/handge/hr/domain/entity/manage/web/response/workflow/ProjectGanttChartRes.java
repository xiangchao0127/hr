package com.handge.hr.domain.entity.manage.web.response.workflow;

/** 项目列表甘特图返回实体
 * create by xc in 2018/08/21
 */
public class ProjectGanttChartRes {
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目负责人
     */
    private String projectLeader;
    /**
     * 项目状态
     */
    private String status;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
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

    public String getPlanStartPoint() {
        return planStartPoint;
    }

    public void setPlanStartPoint(String planStartPoint) {
        this.planStartPoint = planStartPoint;
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

    @Override
    public String toString() {
        return "ProjectGanttChartRes{" +
                "projectName='" + projectName + '\'' +
                ", projectLeader='" + projectLeader + '\'' +
                ", status='" + status + '\'' +
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
