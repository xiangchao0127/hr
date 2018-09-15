package com.handge.hr.domain.entity.manage.web.request.workflow;

import com.handge.hr.domain.entity.base.web.request.PageParam;

import javax.validation.constraints.NotEmpty;

/**
 * create by xc in 2018/08/27
 */
public class TaskDetailsParam extends PageParam {
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

    @Override
    public String toString() {
        return "TaskDetailsParam{" +
                "taskId='" + taskId + '\'' +
                '}';
    }
}
