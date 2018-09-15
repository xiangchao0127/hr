package com.handge.hr.domain.entity.manage.web.response.workflow;

/**
 * create by xc in 2018/09/06
 */
public class TimeRes {
    /**
     * 最小计划开始时间
     */
    private String minPlanStartTime;
    /**
     * 最大计划开始时间
     */
    private String maxPlanEndTime;
    /**
     * 最大实际开始时间
     */
    private String maxActualEndTime;

    public String getMinPlanStartTime() {
        return minPlanStartTime;
    }

    public void setMinPlanStartTime(String minPlanStartTime) {
        this.minPlanStartTime = minPlanStartTime;
    }

    public String getMaxPlanEndTime() {
        return maxPlanEndTime;
    }

    public void setMaxPlanEndTime(String maxPlanEndTime) {
        this.maxPlanEndTime = maxPlanEndTime;
    }

    public String getMaxActualEndTime() {
        return maxActualEndTime;
    }

    public void setMaxActualEndTime(String maxActualEndTime) {
        this.maxActualEndTime = maxActualEndTime;
    }

    @Override
    public String toString() {
        return "TimeRes{" +
                "minPlanStartTime='" + minPlanStartTime + '\'' +
                ", maxPlanEndTime='" + maxPlanEndTime + '\'' +
                ", maxActualEndTime='" + maxActualEndTime + '\'' +
                '}';
    }
}
