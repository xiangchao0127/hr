package com.handge.hr.manage.service.api.workflow;

public interface ICompletion {
    /**
     * 获取项目完成度
     *
     * @param projectId
     * @return
     */
    String getProjectCompletion(String projectId);

    /**
     * 获取任务完成度
     *
     * @param taskId
     * @return
     */
    String getTaskCompletion(String taskId);
}
