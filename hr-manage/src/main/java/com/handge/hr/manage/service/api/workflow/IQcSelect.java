package com.handge.hr.manage.service.api.workflow;

import com.handge.hr.domain.entity.manage.web.request.workflow.ProjectQcSelectParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.ProjectQcSubParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.TaskQcSelectParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.TaskQcSubParam;
import com.handge.hr.domain.entity.manage.web.response.workflow.TaskQcRes;

import java.util.List;

/**
 * create by xc in 2018/08/30
 */
public interface IQcSelect {

    /**
     * QC项目列表
     */
    List<String> getQcProjectList(ProjectQcSelectParam projectQcSelectParam);

    /**
     * QC任务列表
     */
    List<TaskQcRes> getQcTaskList(TaskQcSelectParam taskQcSelectParam);

    /**
     * 获取项目下级任务
     */
    List<String> getProjectSubTask(ProjectQcSubParam projectQcSubParam);

    /**
     * 获取任务下级子任务
     */
    List<String> getSubTask(TaskQcSubParam taskQcSubParam);

}
