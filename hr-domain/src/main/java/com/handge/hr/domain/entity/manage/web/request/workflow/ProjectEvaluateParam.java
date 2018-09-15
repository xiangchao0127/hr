package com.handge.hr.domain.entity.manage.web.request.workflow;

import com.handge.hr.domain.entity.manage.web.request.pointcard.AddPointCardParam;

import javax.validation.constraints.NotEmpty;

/**
 * Created by MaJianfu on 2018/8/16.
 */
public class ProjectEvaluateParam {
    /**
     * 项目id
     */
    @NotEmpty
    private String projectId;
    /**
     *任务评价
     */
    AddPointCardParam addPointCardParam;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public AddPointCardParam getAddPointCardParam() {
        return addPointCardParam;
    }

    public void setAddPointCardParam(AddPointCardParam addPointCardParam) {
        this.addPointCardParam = addPointCardParam;
    }
}
