package com.handge.hr.domain.entity.manage.web.request.workflow;

import javax.validation.constraints.NotEmpty;

/**
 * Created by MaJianfu on 2018/8/16.
 */
public class SubmitTaskParam {
    /**
     * 任务id
     */
    @NotEmpty
    private String taskId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

}
