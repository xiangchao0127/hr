package com.handge.hr.manage.service.api.workflow;

import com.handge.hr.common.utils.PageResults;
import com.handge.hr.domain.entity.manage.web.request.workflow.*;
import com.handge.hr.domain.entity.manage.web.response.workflow.TaskDetailsRes;
import com.handge.hr.domain.entity.manage.web.response.workflow.TaskHistoryRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MaJianfu on 2018/8/16.
 */
public interface ITask {
    /**
     * 分配任务
     */
    int addTask(List<AddTaskParam> addTaskParamList);

    /**
     * 修改任务
     */
    int motifyTask(AddTaskParam addTaskParam);

    /**
     * 接收任务
     */
    int receiveTask(List<ReceiveTaskParam> receiveTaskParamList);

    /**
     * QC分配
     */
    int taskQcDistribution(List<QcDistributionParam> qcDistributionParamList);

    /**
     * 提交任务
     */
    int submitTask(List<SubmitTaskParam> submitTaskParamList);

    /**
     * 删除任务
     */
    int deleteTask(DeleteTaskParam deleteTaskParam);

    /**
     * 终止任务
     */
    int terminatedTask(List<TerminatedTaskParam> terminatedTaskParamList);

    /**
     * QC评价
     */
    int qcEvaluate(QcEvaluateParam qcEvaluateParam);

    /**
     * 任务评价
     */
    int taskEvaluate(TaskEvaluateParam taskEvaluateParam);

    /**
     * 获取任务列表
     *
     * @return
     */
    PageResults getTaskList(TaskSelectParam taskSelectParam);

    /**
     * 任务详情
     */
    TaskDetailsRes getTaskDetails(TaskDetailsParam taskDetailsParam);

    /**
     * 获取任务历史记录
     */
    ArrayList<TaskHistoryRes> getTaskHistory(TaskHistorySelectParam param);


}
