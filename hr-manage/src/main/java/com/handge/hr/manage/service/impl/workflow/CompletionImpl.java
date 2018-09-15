package com.handge.hr.manage.service.impl.workflow;

import com.handge.hr.domain.repository.mapper.WorkflowProjectMapper;
import com.handge.hr.domain.repository.mapper.WorkflowTaskMapper;
import com.handge.hr.domain.repository.pojo.WorkflowTask;
import com.handge.hr.manage.common.utils.CompletionRuleUtils;
import com.handge.hr.manage.service.api.workflow.ICompletion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CompletionImpl implements ICompletion {
    @Autowired
    WorkflowTaskMapper workflowTaskMapper;
    @Autowired
    WorkflowProjectMapper workflowProjectMapper;

    @Override
    public String getProjectCompletion(String projectId) {
        Example example = new Example(WorkflowTask.class);
        example.createCriteria().andEqualTo("projectId", projectId);
        List<WorkflowTask> workflowTasks = workflowTaskMapper.selectByExample(example);
        String taskCompletion = CompletionRuleUtils.getTaskCompletion(workflowTasks);
        String result = new BigDecimal(taskCompletion).multiply(new BigDecimal("100")).setScale(0, 1).toPlainString();
        return result;
    }

    @Override
    public String getTaskCompletion(String taskId) {
        List<WorkflowTask> workflowTasks = workflowTaskMapper.getChildTasksById(taskId);
        String taskCompletion = CompletionRuleUtils.getTaskCompletion(workflowTasks);
        System.out.println(taskCompletion);
        String result = new BigDecimal(taskCompletion).multiply(new BigDecimal("100")).setScale(0, 1).toPlainString();
        return result;
    }
}
