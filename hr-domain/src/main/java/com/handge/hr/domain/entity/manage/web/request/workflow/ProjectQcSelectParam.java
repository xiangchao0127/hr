package com.handge.hr.domain.entity.manage.web.request.workflow;

/**
 * create by xc in 2018/08/30
 */
public class ProjectQcSelectParam {
    /**
     * 项目名称
     */
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "ProjectQcSelectParam{" +
                "projectName='" + projectName + '\'' +
                '}';
    }
}
