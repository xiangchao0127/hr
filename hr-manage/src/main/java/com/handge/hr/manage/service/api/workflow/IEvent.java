package com.handge.hr.manage.service.api.workflow;

/**
 * Created by MaJianfu on 2018/9/5.
 */
public interface IEvent {
    /**
     * 往项目事件表插入记录
     */
    void addProjectEvent(String projectId, String projectType, String description);

    /**
     * 往任务事件表插入记录
     */
    void addTaskEvent(String taskId, String taskType, String description);
}
