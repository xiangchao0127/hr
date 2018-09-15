package com.handge.hr.manage.service.impl.workflow;

import com.handge.hr.auth.service.api.IMyToken;
import com.handge.hr.common.enumeration.manage.ProjectStatusEnum;
import com.handge.hr.common.enumeration.manage.TaskStatusEnum;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.entity.manage.web.request.workflow.ProjectQcSelectParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.ProjectQcSubParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.TaskQcSelectParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.TaskQcSubParam;
import com.handge.hr.domain.entity.manage.web.response.workflow.TaskQcRes;
import com.handge.hr.domain.repository.mapper.*;
import com.handge.hr.domain.repository.pojo.WorkflowProject;
import com.handge.hr.domain.repository.pojo.WorkflowTask;
import com.handge.hr.manage.activiti.ActivitiServer;
import com.handge.hr.manage.service.api.workflow.ICompletion;
import com.handge.hr.manage.service.api.workflow.IQcSelect;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create by xc in 2018/08/30
 */
public class QcSelectImpl implements IQcSelect {

    @Autowired
    WorkflowProjectMapper workflowProjectMapper;
    @Autowired
    ICompletion iCompletion;
    @Autowired
    ActivitiServer activitiServer;
    @Autowired
    DictCommonMapper dictCommonMapper;
    @Autowired
    DictCommonTypeMapper dictCommonTypeMapper;
    @Autowired
    EntityEmployeeMapper entityEmployeeMapper;
    @Autowired
    EntityDepartmentMapper entityDepartmentMapper;
    @Autowired
    WorkflowTaskMapper workflowTaskMapper;
    @Autowired
    WorkflowTaskEventMapper workflowTaskEventMapper;
    @Autowired
    TaskService taskService;
    @Autowired
    IMyToken myToken;
    @Autowired
    WorkflowProjectEventMapper workflowProjectEventMapper;

    @Override
    public List<String> getQcProjectList(ProjectQcSelectParam projectQcSelectParam) {
        String userId = myToken.getUserInfo().getEmployee().getId();
        Example example = new Example(WorkflowProject.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("principal", userId);
        criteria.andNotIn("status", Arrays.asList(ProjectStatusEnum.COMPLETED.getValue(), ProjectStatusEnum.TERMINATION.getValue()));
        if (StringUtils.notEmpty(projectQcSelectParam.getProjectName())) {
            criteria.andLike("name", "%" + projectQcSelectParam.getProjectName() + "%");
        }
        List<WorkflowProject> workflowProjects = workflowProjectMapper.selectByExample(example);
        List<String> projectList = new ArrayList<>();
        workflowProjects.forEach(r -> {
            projectList.add(r.getName());
        });
        return projectList;
    }

    @Override
    public List<TaskQcRes> getQcTaskList(TaskQcSelectParam taskQcSelectParam) {
        String userId = myToken.getUserInfo().getEmployee().getId();
        Example example = new Example(WorkflowTask.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("receiver", userId);
        criteria.andEqualTo("status", TaskStatusEnum.QC.getValue());
        if (StringUtils.notEmpty(taskQcSelectParam.getTaskName())) {
            criteria.andLike("name", "%" + taskQcSelectParam.getTaskName() + "%");
        }
        if (StringUtils.notEmpty(taskQcSelectParam.getTaskLevel())) {
            criteria.andEqualTo("depth", taskQcSelectParam.getTaskLevel());
        }
        List<WorkflowTask> workflowTasks = workflowTaskMapper.selectByExample(example);
        List<TaskQcRes> taskList = new ArrayList<>();
        workflowTasks.forEach(r -> {
            TaskQcRes taskQcRes = new TaskQcRes();
            taskQcRes.setTaskName(r.getName());
            taskQcRes.setTaskLevel(r.getDepth().toString() + "级");
            taskList.add(taskQcRes);
        });
        return taskList;
    }

    @Override
    public List<String> getProjectSubTask(ProjectQcSubParam projectQcSubParam) {
        //项目id
        String projectId = projectQcSubParam.getProjectId();
        //任务名称
        String taskName = projectQcSubParam.getTaskName();
        Example example = new Example(WorkflowTask.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("projectId", projectId);
        criteria.andEqualTo("depth", 1);
        if (StringUtils.notEmpty(taskName)) {
            criteria.andLike("name", "%" + taskName + "%");
        }
        List<WorkflowTask> workflowTasks = workflowTaskMapper.selectByExample(example);
        List<String> taskList = new ArrayList<>();
        workflowTasks.forEach(r -> {
            taskList.add(r.getName());
        });
        return taskList;
    }

    @Override
    public List<String> getSubTask(TaskQcSubParam taskQcSubParam) {
        //任务id
        String taskId = taskQcSubParam.getTaskId();
        //任务名称
        String taskName = taskQcSubParam.getTaskName();
        Example example = new Example(WorkflowTask.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentTaskId", taskId);
        if (StringUtils.notEmpty(taskName)) {
            criteria.andLike("name", "%" + taskName + "%");
        }
        List<WorkflowTask> workflowTasks = workflowTaskMapper.selectByExample(example);
        List<String> taskList = new ArrayList<>();
        workflowTasks.forEach(r -> {
            taskList.add(r.getName());
        });
        return taskList;
    }
}
