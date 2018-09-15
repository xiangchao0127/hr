package com.handge.hr.domain.entity.manage.web.response.workflow;

import java.util.ArrayList;

/**
 * create by xc in 2018/09/06
 */
public class ProjectHistoryRes {
    private String date;
    private ArrayList<ProjectHistorySub> projectHistorySubs;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ProjectHistorySub> getProjectHistorySubs() {
        return projectHistorySubs;
    }

    public void setProjectHistorySubs(ArrayList<ProjectHistorySub> projectHistorySubs) {
        this.projectHistorySubs = projectHistorySubs;
    }

    @Override
    public String toString() {
        return "ProjectHistoryRes{" +
                "date='" + date + '\'' +
                ", projectHistorySubs=" + projectHistorySubs +
                '}';
    }
}
