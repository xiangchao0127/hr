package com.handge.hr.domain.entity.manage.web.request.workflow;

import com.handge.hr.domain.entity.manage.web.request.pointcard.AddPointCardParam;

import javax.validation.constraints.NotEmpty;

/**
 * Created by MaJianfu on 2018/8/16.
 */
public class TaskEvaluateParam {
    /**
     * 任务id
     */
    @NotEmpty
    private String taskId;
    /**
     *任务评价
     */
    AddPointCardParam addPointCardParam;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public AddPointCardParam getAddPointCardParam() {
        return addPointCardParam;
    }

    public void setAddPointCardParam(AddPointCardParam addPointCardParam) {
        this.addPointCardParam = addPointCardParam;
    }
}
