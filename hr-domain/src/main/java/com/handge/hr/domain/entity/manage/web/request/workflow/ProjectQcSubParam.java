package com.handge.hr.domain.entity.manage.web.request.workflow;

/**
 * create by xc in 2018/08/30
 */
public class ProjectQcSubParam {
    /**
     * 项目Id
     */
    private String projectId;
    /**
     * 任务名称
     */
    private String taskName;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String toString() {
        return "ProjectQcSubParam{" +
                "projectId='" + projectId + '\'' +
                ", taskName='" + taskName + '\'' +
                '}';
    }
}
