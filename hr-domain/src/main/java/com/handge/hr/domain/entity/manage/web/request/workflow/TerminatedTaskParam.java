package com.handge.hr.domain.entity.manage.web.request.workflow;


/**
 * Created by MaJianfu on 2018/8/16.
 */
public class TerminatedTaskParam {
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 终止原因
     */
    private String reason;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
