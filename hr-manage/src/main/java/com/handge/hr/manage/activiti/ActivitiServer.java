package com.handge.hr.manage.activiti;

import com.handge.hr.common.enumeration.manage.BooleanEnum;
import com.handge.hr.common.utils.FileConfigurationUtils;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;

/**
 * activiti工作流服务
 *
 * @author: gxy
 * @date: 18-8-2
 */
@Service
public class ActivitiServer {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    /**
     * 用于自动部署流程
     */
    public ActivitiServer(ProcessEngine processEngine) {
        String process = "process";
        if (BooleanEnum.NO.getValue().equals(FileConfigurationUtils.getConfig(process))) {
            try {
                String taskProcessPath = "taskProcessPath";
                processEngine.getRepositoryService().createDeployment().addInputStream(FileConfigurationUtils.getConfig(taskProcessPath), new FileInputStream(FileConfigurationUtils.getConfig("taskProcessPath"))).deploy();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                exit(0);
            }
        }
    }

    /**
     * 创建流程实例
     *
     * @param businessKey 业务id
     * @param var         流程变量
     */
    @SafeVarargs
    public final void startProcessInstance(String businessKey, Map<String, Object>... var) {
        String processName = "processName";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(FileConfigurationUtils.getConfig(processName), businessKey, var[0]);
        String processId = processInstance.getId();
        logger.info("流程创建成功，当前流程实例ID：" + processId);
    }

    /**
     * 完成流程中的一个任务
     *
     * @param businessKey 业务id
     * @param assignee    受理人
     * @param var         流程变量
     * @return status 业务状态
     */
    @SafeVarargs
    public final String complete(String businessKey, String assignee, Map<String, Object>... var) {
        String processInstanceId = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult().getProcessInstanceId();
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).processInstanceId(processInstanceId).list();

        if (list.size() == 0) {
            throw new UnifiedException("未查询到此任务的相关流程", ExceptionWrapperEnum.DB_Result_IS_NULL);
        }

        printTask(list);

        Task currentTask = list.get(0);
        String currentTaskId = currentTask.getId();
        String name = currentTask.getName();
        String existQC = "1";

        String qcDistribution = "QC分配";
        String hasQC = "";
        String isFinished = "";
        if (qcDistribution.equals(currentTask.getTaskDefinitionKey())) {
            hasQC = String.valueOf(var[0].get("hasQC"));
            isFinished = taskService.getVariable(currentTaskId, "isFinished") != null ? String.valueOf(taskService.getVariable(currentTaskId, "isFinished")) : null;
        }

        String submitTask = "完成任务";
        if (submitTask.equals(currentTask.getTaskDefinitionKey())) {
            hasQC = taskService.getVariable(currentTaskId, "hasQC") != null ? String.valueOf(taskService.getVariable(currentTaskId, "hasQC")) : null;
        }

        if (var.length > 0) {
            taskService.complete(currentTaskId, var[0]);
        } else {
            taskService.complete(currentTaskId);
        }

        logger.info(currentTask.getTaskDefinitionKey() + "已完成");

        if (qcDistribution.equals(currentTask.getTaskDefinitionKey())) {
            if (isFinished == null) {
                return null;
            } else {
                if (existQC.equals(hasQC)) {
                    return null;
                } else {
                    return name;
                }
            }
        } else if (submitTask.equals(currentTask.getTaskDefinitionKey())) {
            if (hasQC == null) {
                return name;
            } else {
                if (existQC.equals(hasQC)) {
                    return name;
                } else {
                    return "04";
                }
            }
        } else {
            return name;
        }
    }

    /**
     * 设置qc任务分配人
     *
     * @param businessKeys 业务id集合
     * @param qcLeader     分配人
     */
    public void setQCLeader(List<String> businessKeys, String qcLeader) {
        for (String businessKey : businessKeys) {
            String processInstanceId = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult().getProcessInstanceId();
            runtimeService.setVariable(processInstanceId, "QCID", qcLeader);

            //触发生成qc分配任务
            qcDistributionTrigger(businessKey);
        }
    }

    /**
     * 取消流程
     *
     * @param businessKey 业务id
     * @return status 状态
     */
    public String cancelProcess(String businessKey) {
        String processInstanceId = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult().getProcessInstanceId();
        List<Execution> list = runtimeService.createExecutionQuery().signalEventSubscriptionName("取消流程").list();

        final String[] status = {""};
        list.forEach(execution -> {
            if (execution.getProcessInstanceId().equals(processInstanceId)) {
                runtimeService.signalEventReceived("取消流程", execution.getId());
                logger.info("流程" + processInstanceId + "已取消！！！");

                status[0] = execution.getName();
            }
        });

        if ("".equals(status[0])) {
            throw new UnifiedException("当前任务状态不支持此操作", ExceptionWrapperEnum.Parameter_Enum_NOT_Match);
        }

        return status[0];
    }

    /**
     * 触发生成qc分配任务
     *
     * @param businessKey 业务id
     */
    public void qcDistributionTrigger(String businessKey) {
        String processInstanceId = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult().getProcessInstanceId();
        List<Execution> list = runtimeService.createExecutionQuery().signalEventSubscriptionName("触发qc分配").list();
        list.forEach(execution -> {
            if (execution.getProcessInstanceId().equals(processInstanceId)) {
                runtimeService.signalEventReceived("触发qc分配", execution.getId());
                logger.info("qc分配已触发！！！");
            }
        });
    }

    /**
     * 修改任务受理人
     *
     * @param businessKey 业务id
     * @param newAssignee 新的受理人
     */
    public void modifyReceiver(String businessKey, String newAssignee) {
        String processInstanceId = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult().getProcessInstanceId();
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskName("确认接收").singleResult();
        if (task == null) {
            task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskName("完成任务").singleResult();
        }
        task.setAssignee(newAssignee);
        taskService.saveTask(task);

        logger.info("已将任务受理人修改为" + newAssignee);
    }

    /**
     * 确认接收之后，修改QC受理人
     *
     * @param businessKey 业务id
     * @param newQC       新的QC受理人
     */
    public void modifyQC(String businessKey, String newQC) {
        String processInstanceId = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult().getProcessInstanceId();
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskName("QC评价").singleResult();
        if (task != null) {
            task.setAssignee(newQC);
            taskService.saveTask(task);
        } else {
            runtimeService.setVariable(processInstanceId, "qcID", newQC);
        }

        logger.info("已将QC受理人修改为" + newQC);
    }

    /**
     * 打印任务相关信息
     *
     * @param list 任务列表
     */
    private void printTask(List<Task> list) {
        if (list != null && list.size() > 0) {
            for (org.activiti.engine.task.Task task : list) {
                logger.info("任务ID：" + task.getId());
                logger.info("任务的办理人：" + task.getAssignee());
                logger.info("任务名称：" + task.getTaskDefinitionKey());
                logger.info("任务的创建时间：" + task.getCreateTime());
                logger.info("流程实例ID：" + task.getProcessInstanceId());
                logger.info("##########################################");
            }
        }
    }
}
