package com.handge.hr.domain.entity.manage.web.response.workflow;

import java.util.ArrayList;

/**
 * create by xc in 2018/09/07
 */
public class TaskHistoryRes {
    private String date;
    private ArrayList<TaskHistorySub> taskHistorySubs;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<TaskHistorySub> getTaskHistorySubs() {
        return taskHistorySubs;
    }

    public void setTaskHistorySubs(ArrayList<TaskHistorySub> taskHistorySubs) {
        this.taskHistorySubs = taskHistorySubs;
    }

    @Override
    public String toString() {
        return "TaskHistoryRes{" +
                "date='" + date + '\'' +
                ", taskHistorySubs=" + taskHistorySubs +
                '}';
    }
}
