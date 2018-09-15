package com.handge.hr.domain.entity.manage.web.response.workflow;

import com.handge.hr.common.utils.PageResults;

import java.util.List;

/**
 * create by xc in 2018/09/06
 */
public class ProjectGanttChart {
    /**
     * 最小开始时间
     */
    private String minStartTime;
    /**
     * 最大结束时间
     */
    private String maxEndTime;

    private PageResults pageResults;

    public String getMinStartTime() {
        return minStartTime;
    }

    public void setMinStartTime(String minStartTime) {
        this.minStartTime = minStartTime;
    }

    public String getMaxEndTime() {
        return maxEndTime;
    }

    public void setMaxEndTime(String maxEndTime) {
        this.maxEndTime = maxEndTime;
    }

    public PageResults getPageResults() {
        return pageResults;
    }

    public void setPageResults(PageResults pageResults) {
        this.pageResults = pageResults;
    }

    @Override
    public String toString() {
        return "ProjectGanttChart{" +
                "minStartTime='" + minStartTime + '\'' +
                ", maxEndTime='" + maxEndTime + '\'' +
                ", pageResults=" + pageResults +
                '}';
    }
}
