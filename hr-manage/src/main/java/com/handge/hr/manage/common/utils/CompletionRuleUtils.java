package com.handge.hr.manage.common.utils;

import com.handge.hr.common.enumeration.manage.TaskStatusEnum;
import com.handge.hr.domain.repository.pojo.WorkflowTask;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;

import java.util.Date;
import java.util.List;

/**
 * 任务(项目)完成度计算规则工具类
 * create by xc in 2018/08/22
 */
public class CompletionRuleUtils {
    /**
     * 任务完成度计算规则
     *
     * @param workflowTasks 字任务集合
     * @return 任务完成度
     */
    public static String getTaskCompletion(List<WorkflowTask> workflowTasks) {
        double ratio = 0.0;
        if (workflowTasks.size() == 0) {
            return String.valueOf(ratio);
        }
        for (WorkflowTask workflowTask : workflowTasks) {
            Double taskStatusRatio = getTaskStatusRatio(TaskStatusEnum.getEnumByValue(workflowTask.getStatus()), workflowTask);
            ratio += taskStatusRatio;
        }
        return String.valueOf(ratio / (workflowTasks.size()));
    }

    /**
     * 获取任务状态系数
     *
     * @param taskStatusEnum
     * @return
     */
    public static Double getTaskStatusRatio(TaskStatusEnum taskStatusEnum, WorkflowTask workflowTask) {
        double ratio = 0;
        switch (taskStatusEnum) {
            case UNRECEIVED:
                ratio = 0.1;
                break;
            case MAKING:
                ratio = getTimeoutOrMakingCompletion(workflowTask.getActualStartTime(), workflowTask.getPlanStartTime());
                break;
            case COMPLETED:
                ratio = 1.0;
                break;
            case TERMINATION:
                ratio = 1.0;
                break;
            case EVALUATE:
                ratio = 1.0;
                break;
            case QC:
                ratio = 0.8;
                break;
            default:
                throw new UnifiedException("任务状态异常", ExceptionWrapperEnum.IllegalArgumentException);
        }
        return ratio;
    }


    /**
     * 获取进行中或超时未完成状态系数
     *
     * @param actualStartTime 实际开始时间
     * @param planEndTime     计划结束时间
     * @return
     */
    public static double getTimeoutOrMakingCompletion(Date actualStartTime, Date planEndTime) {
        double ratio = 0.0;
        //当前时间
        Date nowTime = new Date();
        ratio = (nowTime.getTime() - actualStartTime.getTime()) / (planEndTime.getTime() - actualStartTime.getTime());
        return ratio * 0.5;
    }

}
