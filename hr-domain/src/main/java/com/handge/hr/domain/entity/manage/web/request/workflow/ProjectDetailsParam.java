package com.handge.hr.domain.entity.manage.web.request.workflow;


import com.github.pagehelper.page.PageParams;
import com.handge.hr.domain.entity.base.web.request.PageParam;

import javax.validation.constraints.NotEmpty;

/**
 * Created by MaJianfu on 2018/8/16.
 */
public class ProjectDetailsParam extends PageParam {
    /**
     * 项目id
     */
    @NotEmpty
    private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
