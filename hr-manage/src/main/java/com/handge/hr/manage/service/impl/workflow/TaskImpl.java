package com.handge.hr.manage.service.impl.workflow;

import com.github.pagehelper.PageHelper;
import com.handge.hr.auth.service.api.IMyToken;
import com.handge.hr.common.enumeration.base.DateFormatEnum;
import com.handge.hr.common.enumeration.manage.*;
import com.handge.hr.common.utils.*;
import com.handge.hr.domain.entity.manage.web.request.pointcard.AddPointCardParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.*;
import com.handge.hr.domain.entity.manage.web.response.workflow.*;
import com.handge.hr.domain.repository.mapper.*;
import com.handge.hr.domain.repository.pojo.*;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import com.handge.hr.manage.activiti.ActivitiServer;
import com.handge.hr.manage.common.description.EventDescription;
import com.handge.hr.manage.common.utils.UuidUtils;
import com.handge.hr.manage.service.api.workflow.IPointCard;
import com.handge.hr.manage.service.api.workflow.ICompletion;
import com.handge.hr.manage.service.api.workflow.IEvent;
import com.handge.hr.manage.service.api.workflow.ITask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by MaJianfu on 2018/8/16.
 */
@SuppressWarnings({"unchecked", "Duplicates"})
@Service
public class TaskImpl implements ITask {

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
    IMyToken myToken;
    @Autowired
    WorkflowProjectEventMapper workflowProjectEventMapper;
    @Autowired
    IPointCard iPointCard;
    @Autowired
    IEvent addEvent;
    ArrayList arrayListDesc = new ArrayList();

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addTask(List<AddTaskParam> addTaskParamList) {
        String createById = myToken.getUserInfo().getEmployee().getId();
        for (AddTaskParam taskParam : addTaskParamList) {
            //1.分配任务
            String projectStatus = workflowProjectMapper.selectByPrimaryKey(taskParam.getProjectId()).getStatus();
            String parentTaskStatus = "";
            if (StringUtils.notEmpty(taskParam.getParentTaskId())) {
                parentTaskStatus = workflowTaskMapper.selectByPrimaryKey(taskParam.getParentTaskId()).getStatus();
            } else {
                parentTaskStatus = TaskStatusEnum.MAKING.getValue();
            }
            if (ProjectStatusEnum.MAKING.getValue().equals(projectStatus) && TaskStatusEnum.MAKING.getValue().equals(parentTaskStatus)) {
                WorkflowTask task = new WorkflowTask();
                task.setId(UuidUtils.getUUid());
                task.setGmtCreate(new Date());
                task.setProjectId(taskParam.getProjectId());
                task.setParentTaskId(taskParam.getParentTaskId());
                task.setName(taskParam.getTaskName());
                task.setType(taskParam.getType());
                task.setContent(taskParam.getContent());
                if (StringUtils.notEmpty(taskParam.getDepartmentId()) && StringUtils.isEmpty(taskParam.getReceiver())) {
                    task.setDepartmentId(taskParam.getDepartmentId());
                    task.setReceiver(entityDepartmentMapper.getDepartmentEmployeeById(taskParam.getDepartmentId()));
                } else {
                    task.setDepartmentId(workflowTaskMapper.getDepartmentById(task.getParentTaskId()));
                    task.setReceiver(taskParam.getReceiver());
                }
                try {
                    if (StringUtils.notEmpty(taskParam.getPlanStartTime())) {
                        task.setPlanStartTime(DateUtil.str2Date(DateFormatEnum.SECONDS, taskParam.getPlanStartTime()));
                    }
                    if (StringUtils.notEmpty(taskParam.getPlanEndTime())) {
                        task.setPlanEndTime(DateUtil.str2Date(DateFormatEnum.SECONDS, taskParam.getPlanEndTime()));
                    }
                } catch (ParseException e) {
                    throw new UnifiedException(e);
                }
                task.setDifficult(taskParam.getDifficult());
                task.setUrgency(taskParam.getUrgency());
                task.setWorkload(taskParam.getWorkload());
                if (StringUtils.notEmpty(taskParam.getType())) {
                    String date = DateUtil.date2Str(new Date(), DateFormatEnum.DAYNEW);
                    LinkedList<String> list = workflowTaskMapper.getNumber(date);
                    String num = "";
                    // 通过比较器来实现排序
                    if (null == list || list.size() == 0) {
                        num = "0001";
                    } else {
                        Collections.sort(list, new Comparator<String>() {
                            @Override
                            public int compare(String o1, String o2) {
                                return -Double.compare(Double.parseDouble(o1.substring(2)),
                                        Double.parseDouble(o2.substring(2)));
                            }
                        });
                        int maxNum = Integer.parseInt(list.get(0).substring(10));
                        AtomicInteger atomicNum = new AtomicInteger();
                        atomicNum.set(maxNum);
                        int newNum = atomicNum.incrementAndGet();
                        num = String.format("%04d", newNum);
                    }
                    task.setNumber(NumberTypeEnum.TASK.getValue() + date + num);
                }
                task.setCreatedBy(createById);
                task.setRepeat((short) -1);
                if (StringUtils.notEmpty(task.getParentTaskId())) {
                    int depth = workflowTaskMapper.getDepth(task.getParentTaskId());
                    task.setDepth((short) (depth + 1));
                } else {
                    task.setDepth((short) 1);
                }
                task.setIsFirst(BooleanEnum.YES.getValue());
                //流程开始
                Map<String, Object> variables = new HashMap<>(3);
                // 设置分配任务task的受理人变量
                variables.put("userID", task.getCreatedBy());
                // 设置确认接收/完成任务task的受理人变量
                variables.put("receiverID", task.getReceiver());
                // 设置QC分配task的受理人变量
                String projectType = workflowProjectMapper.selectByPrimaryKey(task.getProjectId()).getType();
                //如果是一级任务，则直接指定部门领导人为QC分配人，否则者在创建任务时，看上级任务是否已经分配qc执行人，如果已分配，则指定此人为此任务的QC分配人
                if (StringUtils.isEmpty(task.getParentTaskId())) {
                    variables.put("QCID", entityDepartmentMapper.getDepartmentEmployeeIdByTypeId(projectType));
                } else {
                    String qcPerson = workflowTaskMapper.selectByPrimaryKey(task.getParentTaskId()).getQcPerson();
                    if (StringUtils.notEmpty(qcPerson)) {
                        variables.put("QCID", qcPerson);
                    }
                }
                activitiServer.startProcessInstance(task.getId(), variables);
                //工作流分配任务
                String status = activitiServer.complete(task.getId(), task.getCreatedBy());
                task.setStatus(status);
                workflowTaskMapper.insertSelective(task);
                //2.任务事件记录
                String name = entityEmployeeMapper.getEmployeeNameById(task.getCreatedBy());
                if (StringUtils.isEmpty(task.getParentTaskId())) {
                    String description = EventDescription.createTaskByProject(name, task.getName(), NumberTypeEnum.TASK.getDescription());
                    addEvent.addProjectEvent(task.getProjectId(), ProjectEventEnum.DIRECT_TASK_CREATE.getValue(), description);
                }
                String description = EventDescription.create(name, NumberTypeEnum.TASK.getDescription());
                addEvent.addTaskEvent(task.getId(), ProjectEventEnum.CREATE.getValue(), description);
            } else {
                throw new UnifiedException("上级任务没有接收，无法分配", ExceptionWrapperEnum.Data_Outside_Special_Range);
            }
        }
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int motifyTask(AddTaskParam modifyTaskParam) {
        //1.修改任务
        WorkflowTask workflowTask = workflowTaskMapper.selectByPrimaryKey(modifyTaskParam.getTaskId());
        workflowTask.setGmtModified(new Date());
        ProjectEventRes eventRes = new ProjectEventRes();
        List<String> list = new ArrayList<>();
        if (StringUtils.notEmpty(modifyTaskParam.getTaskName()) && !modifyTaskParam.getTaskName().equals(workflowTask.getName())) {
            eventRes.setBeforeModified(workflowTask.getName());
            workflowTask.setName(modifyTaskParam.getTaskName());
            eventRes.setAfterModified(workflowTask.getName());
            addDesc(TaskModifyEnum.TASKNAME.getValue(), eventRes.getBeforeModified(), eventRes.getAfterModified());
        }
        if (StringUtils.notEmpty(modifyTaskParam.getType()) && !modifyTaskParam.getType().equals(workflowTask.getType())) {
            eventRes.setBeforeModified(workflowTask.getType());
            workflowTask.setType(dictCommonMapper.getCode(DictCommonTypeEnum.TASK_TYPE.getValue(), modifyTaskParam.getType()));
            eventRes.setAfterModified(workflowTask.getType());
            addDesc(TaskModifyEnum.TYPE.getValue(), eventRes.getBeforeModified(), eventRes.getAfterModified());
        }
        if (StringUtils.notEmpty(modifyTaskParam.getDifficult()) && !modifyTaskParam.getDifficult().equals(workflowTask.getDifficult())) {
            eventRes.setBeforeModified(workflowTask.getDifficult());
            workflowTask.setDifficult(dictCommonMapper.getCode(DictCommonTypeEnum.DIFFICULT.getValue(), modifyTaskParam.getDifficult()));
            eventRes.setAfterModified(workflowTask.getDifficult());
            addDesc(TaskModifyEnum.DIFFICULT.getValue(), eventRes.getBeforeModified(), eventRes.getAfterModified());
        }
        if (StringUtils.notEmpty(modifyTaskParam.getUrgency()) && !modifyTaskParam.getUrgency().equals(workflowTask.getUrgency())) {
            eventRes.setBeforeModified(workflowTask.getUrgency());
            workflowTask.setUrgency(dictCommonMapper.getCode(DictCommonTypeEnum.URGENCY.getValue(), modifyTaskParam.getUrgency()));
            eventRes.setAfterModified(workflowTask.getUrgency());
            addDesc(TaskModifyEnum.URGENCY.getValue(), eventRes.getBeforeModified(), eventRes.getAfterModified());
        }
        if (StringUtils.notEmpty(modifyTaskParam.getWorkload()) && !modifyTaskParam.getWorkload().equals(workflowTask.getWorkload())) {
            eventRes.setBeforeModified(workflowTask.getWorkload());
            workflowTask.setWorkload(dictCommonMapper.getCode(DictCommonTypeEnum.WORKLOAD.getValue(), modifyTaskParam.getWorkload()));
            eventRes.setAfterModified(workflowTask.getWorkload());
            addDesc(TaskModifyEnum.WORKLOAD.getValue(), eventRes.getBeforeModified(), eventRes.getAfterModified());
        }
        if (StringUtils.notEmpty(modifyTaskParam.getReceiver()) && !modifyTaskParam.getReceiver().equals(workflowTask.getReceiver())) {
            eventRes.setBeforeModified(entityEmployeeMapper.getEmployeeNameById(workflowTask.getReceiver()));
            workflowTask.setReceiver(modifyTaskParam.getReceiver());
            eventRes.setAfterModified(entityEmployeeMapper.getEmployeeNameById(workflowTask.getReceiver()));
            activitiServer.modifyReceiver(workflowTask.getId(), modifyTaskParam.getReceiver());
            addDesc(TaskModifyEnum.RECEIVER.getValue(), eventRes.getBeforeModified(), eventRes.getAfterModified());
        }

        //2.任务事件记录
        String name = entityEmployeeMapper.getEmployeeNameById(myToken.getUserInfo().getEmployee().getId());
        String desc = CollectionUtils.listToString(list);
        addEvent.addTaskEvent(modifyTaskParam.getTaskId(), ProjectEventEnum.MODIFY.getValue(), name + desc);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int receiveTask(List<ReceiveTaskParam> receiveTaskParamList) {
        String receiverId = myToken.getUserInfo().getEmployee().getId();
        String name = entityEmployeeMapper.getEmployeeNameById(receiverId);
        for (ReceiveTaskParam receiveTaskParam : receiveTaskParamList) {
            WorkflowTask workflowTask = workflowTaskMapper.selectByPrimaryKey(receiveTaskParam.getTaskId());
            String statusId = TaskStatusEnum.UNRECEIVED.getValue();
            if (statusId.equals(workflowTask.getStatus())) {
                //项目事件记录
                String description = EventDescription.receive(name, NumberTypeEnum.TASK.getDescription());
                addEvent.addTaskEvent(workflowTask.getId(), ProjectEventEnum.RECEIVE.getValue(), description);
                String status = activitiServer.complete(receiveTaskParam.getTaskId(), receiverId);
                //如果是一级任务，则在此触发生成qc分配task
                if (StringUtils.isEmpty(workflowTask.getParentTaskId())) {
                    activitiServer.qcDistributionTrigger(workflowTask.getId());
                }
                workflowTask.setStatus(status);
                workflowTask.setActualStartTime(new Date());
                workflowTaskMapper.updateByPrimaryKeySelective(workflowTask);
            }
        }
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int taskQcDistribution(List<QcDistributionParam> qcDistributionParamList) {
        String qCLeaderId = myToken.getUserInfo().getEmployee().getId();
        String name = entityEmployeeMapper.getEmployeeNameById(qCLeaderId);
        for (QcDistributionParam qcDistributionParam : qcDistributionParamList) {
            WorkflowTask workflowTask = workflowTaskMapper.selectByPrimaryKey(qcDistributionParam.getId());
            Map<String, Object> variables = new HashMap<>(1);
            Boolean isSkipQc = workflowProjectMapper.selectByPrimaryKey(workflowTask.getProjectId()).getIsSkipQc();
            Boolean isStatus = !isSkipQc && BooleanEnum.NO.getValue().equals(qcDistributionParam.getIsQC());
            // 设置QC评价task的受理人变量
            if (isSkipQc || isStatus) {
                // 设置没有QC
                variables.put("hasQC", 0);
                workflowTask.setIsSkipQc(true);
                //项目事件记录
                String description = "";
                if (isSkipQc) {
                    description = EventDescription.skipEvaluateByProject(name, NumberTypeEnum.TASK.getDescription());
                } else {
                    description = EventDescription.skipEvaluate(name, NumberTypeEnum.TASK.getDescription());
                }
                addEvent.addTaskEvent(workflowTask.getId(), ProjectEventEnum.REVIEW_SKIPPING.getValue(), description);
            } else {
                // 设置有QC
                variables.put("hasQC", 1);
                String qcPersonId = qcDistributionParam.getQcPersonId();
                workflowTask.setQcPerson(qcPersonId);
                variables.put("qcID", qcPersonId);
                List<String> list = workflowTaskMapper.getTaskIdByParentTaskId(workflowTask.getId());
                activitiServer.setQCLeader(list, qcPersonId);
            }
            String status = activitiServer.complete(qcDistributionParam.getId(), qCLeaderId, variables);
            if (StringUtils.notEmpty(status)) {
                workflowTask.setStatus(status);
            }
            workflowTaskMapper.updateByPrimaryKeySelective(workflowTask);
        }
        return 1;
    }

    private void addDesc(String name, String before, String after) {
        this.arrayListDesc.add("将" + name + "从" + before + "修改为" + after);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int submitTask(List<SubmitTaskParam> submitTaskParamList) {
        String receiverId = myToken.getUserInfo().getEmployee().getId();
        String name = entityEmployeeMapper.getEmployeeNameById(receiverId);
        for (SubmitTaskParam submitTaskParam : submitTaskParamList) {
            WorkflowTask task = workflowTaskMapper.selectByPrimaryKey(submitTaskParam.getTaskId());
            List<WorkflowTask> tasks = workflowTaskMapper.getChildTasksById(task.getId());
            String completedStatus = TaskStatusEnum.COMPLETED.getValue();
            String terminationStatus = TaskStatusEnum.TERMINATION.getValue();
            for (WorkflowTask workflowTask : tasks) {
                boolean isStatus = !task.getId().equals(workflowTask.getId()) && (completedStatus.equals(workflowTask.getStatus()) || terminationStatus.equals(workflowTask.getStatus()));
                if (isStatus) {
                    throw new UnifiedException("子任务不是已完成状态，无法提交", ExceptionWrapperEnum.Data_Outside_Special_Range);
                }
            }
            Map<String, Object> variables = new HashMap<>(2);
            //已完成
            variables.put("isFinished", 1);
            if (BooleanEnum.YES.getValue().equals(task.getIsFirst())) {
                // 是第一次
                variables.put("isFirst", 1);
                task.setIsFirst(BooleanEnum.NO.getValue());
            } else {
                // 不是第一次
                variables.put("isFirst", 0);
            }
            String status = activitiServer.complete(submitTaskParam.getTaskId(), receiverId, variables);
            task.setStatus(status);
            task.setRepeat((short) (task.getRepeat() + 1));
            workflowTaskMapper.updateByPrimaryKeySelective(task);
            //项目事件记录
            String description = EventDescription.submitTask(name, NumberTypeEnum.TASK.getDescription());
            addEvent.addTaskEvent(task.getId(), ProjectEventEnum.SUBMIT.getValue(), description);
        }
        return 1;
    }

    @Override
    public int deleteTask(DeleteTaskParam deleteTaskParam) {
        String statusId = TaskStatusEnum.UNRECEIVED.getValue();
        List<WorkflowTask> workflowTaskList = workflowTaskMapper.selectByIdList(deleteTaskParam.getIds());
        List<String> idList = new ArrayList<>();
        for (WorkflowTask workflowTask : workflowTaskList) {
            if (statusId.equals(workflowTask.getStatus())) {
                idList.add(workflowTask.getId());
                activitiServer.cancelProcess(workflowTask.getId());
            } else {
                throw new UnifiedException("任务不是未接收状态，无法删除", ExceptionWrapperEnum.Data_Outside_Special_Range);
            }
        }
        workflowTaskMapper.deleteByIdList(idList);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int terminatedTask(List<TerminatedTaskParam> terminatedTaskParamList) {
        String name = entityEmployeeMapper.getEmployeeNameById(myToken.getUserInfo().getEmployee().getId());
        for (TerminatedTaskParam terminatedTaskParam : terminatedTaskParamList) {
            WorkflowTask workflowTask = workflowTaskMapper.selectByPrimaryKey(terminatedTaskParam.getTaskId());
            String makingValue = TaskStatusEnum.MAKING.getValue();
            String qcValue = TaskStatusEnum.QC.getValue();
            String evaluateValue = TaskStatusEnum.EVALUATE.getValue();
            String terminationValue = TaskStatusEnum.TERMINATION.getValue();
            String unreceivedValue = TaskStatusEnum.UNRECEIVED.getValue();
            String completedValue = TaskStatusEnum.COMPLETED.getValue();
            List<WorkflowTask> tasks = workflowTaskMapper.getChildTasksById(workflowTask.getId());
            for (WorkflowTask task : tasks) {
                boolean isNotFirst = !task.getId().equals(workflowTask.getId()) && (makingValue.equals(task.getStatus()) || qcValue.equals(task.getStatus()) || evaluateValue.equals(task.getStatus()));
                boolean isFirst = task.getId().equals(workflowTask.getId()) && (makingValue.equals(task.getStatus()) || qcValue.equals(task.getStatus()) || evaluateValue.equals(task.getStatus()));
                boolean isTerminationStatus = terminationValue.equals(task.getStatus());
                boolean isStatus = unreceivedValue.equals(task.getStatus()) || completedValue.equals(task.getStatus());
                //项目事件记录
                if (!isTerminationStatus && !isStatus) {
                    String status = activitiServer.cancelProcess(task.getId());
                    task.setStatus(status);
                    workflowTaskMapper.updateByPrimaryKeySelective(task);
                    String description = "";
                    if (isFirst) {
                        description = EventDescription.terminate(name, terminatedTaskParam.getReason(), NumberTypeEnum.TASK.getDescription());
                    } else if (isNotFirst) {
                        description = EventDescription.terminateTask(name, NumberTypeEnum.TASK.getDescription());
                    }
                    addEvent.addTaskEvent(task.getId(), ProjectEventEnum.TERMINATION.getValue(), description);
                } else if (isTerminationStatus) {

                } else {
                    throw new UnifiedException("子任务处于“未接受”或者“已完成”状态，无法终止", ExceptionWrapperEnum.Data_Outside_Special_Range);
                }
            }
        }
        return 1;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int qcEvaluate(QcEvaluateParam qcEvaluateParam) {
        String name = entityEmployeeMapper.getEmployeeNameById(myToken.getUserInfo().getEmployee().getId());
        WorkflowTask task = workflowTaskMapper.selectByPrimaryKey(qcEvaluateParam.getTaskId());
        Map<String, Object> variables = new HashMap<>(1);
        //项目事件记录
        String description = "";
        String type = "";
        if (BooleanEnum.YES.getValue().equals(qcEvaluateParam.getIsPassed())) {
            //通过
            variables.put("isPassed", 1);
            type = ProjectEventEnum.REVIEW_PASS.getValue();
            description = EventDescription.reviewPass(name, NumberTypeEnum.TASK.getDescription());
            iPointCard.addPointCard(qcEvaluateParam.getAddPointCardParam());
        } else {
            //不通过
            variables.put("isPassed", 0);
            type = ProjectEventEnum.REVIEW_NO_PASS.getValue();
            description = EventDescription.reviewNoPass(name, NumberTypeEnum.TASK.getDescription(), entityEmployeeMapper.getEmployeeNameById(task.getReceiver()));
        }
        addEvent.addTaskEvent(task.getId(), type, description);
        String status = activitiServer.complete(qcEvaluateParam.getTaskId(), task.getQcPerson(), variables);
        task.setStatus(status);
        workflowTaskMapper.updateByPrimaryKeySelective(task);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int taskEvaluate(TaskEvaluateParam taskEvaluateParam) {
        String name = entityEmployeeMapper.getEmployeeNameById(myToken.getUserInfo().getEmployee().getId());
        WorkflowTask task = workflowTaskMapper.selectByPrimaryKey(taskEvaluateParam.getTaskId());
        String status = activitiServer.complete(taskEvaluateParam.getTaskId(), task.getCreatedBy());
        task.setStatus(status);
        workflowTaskMapper.updateByPrimaryKeySelective(task);
        //直属任务结束
        int depth = 1;
        boolean isFirstTask = task.getDepth() == depth;
        if (isFirstTask) {
            String projectDescription = EventDescription.endTaskByProject(name, task.getName(), NumberTypeEnum.TASK.getDescription());
            addEvent.addProjectEvent(task.getProjectId(), ProjectEventEnum.DIRECT_TASK_END.getValue(), projectDescription);
        }
        //项目事件记录
        String evaluateDescription = EventDescription.evaluate(name, NumberTypeEnum.TASK.getDescription());
        addEvent.addTaskEvent(task.getId(), ProjectEventEnum.EVALUATE.getValue(), evaluateDescription);

        String endDescription = EventDescription.end(name, NumberTypeEnum.TASK.getDescription());
        addEvent.addTaskEvent(task.getId(), ProjectEventEnum.END.getValue(), endDescription);
        iPointCard.addPointCard(taskEvaluateParam.getAddPointCardParam());
        return 1;
    }

    @Override
    public PageResults getTaskList(TaskSelectParam taskSelectParam) {
        Example example = new Example(WorkflowTask.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("receiver", myToken.getUserInfo().getEmployee().getId());
        if (StringUtils.notEmpty(taskSelectParam.getProjectName())) {
            criteria.andEqualTo("projectId", workflowProjectMapper.getProjectIdByName(taskSelectParam.getProjectName()));
        }
        if (StringUtils.notEmpty(taskSelectParam.getSchedule())) {
            String[] schedule = taskSelectParam.getSchedule().split("-");
            String startTime = schedule[0];
            String endTime = schedule[1];
            try {
                criteria.andGreaterThanOrEqualTo("planStartTime", DateUtil.str2Date(DateFormatEnum.DAY, startTime));
            } catch (ParseException e) {
                throw new UnifiedException(e);
            }
            try {
                criteria.andLessThanOrEqualTo("planStartTime", DateUtil.str2Date(DateFormatEnum.DAY, endTime));
            } catch (ParseException e) {
                throw new UnifiedException(e);
            }
        }
        if (StringUtils.notEmpty(taskSelectParam.getTaskLevel())) {
            criteria.andLike("depth", taskSelectParam.getTaskLevel());
        }
        if (StringUtils.notEmpty(taskSelectParam.getUrgency())) {
            criteria.andLike("urgency", taskSelectParam.getUrgency());
        }
        if (StringUtils.notEmpty(taskSelectParam.getStatus())) {
            criteria.andLike("status", taskSelectParam.getStatus());
        }
        List<WorkflowTask> workflowTasks = workflowTaskMapper.selectByExample(example);
        List<TaskListRes> taskList = new ArrayList<>();
        for (WorkflowTask workflowTask : workflowTasks) {
            TaskListRes taskListRes = new TaskListRes();
            taskListRes.setId(workflowTask.getId());
            taskListRes.setTaskName(workflowTask.getName());
            taskListRes.setProjectName(workflowProjectMapper.selectByPrimaryKey(workflowTask.getProjectId()).getName());
            taskListRes.setChargeName(workflowTask.getReceiver());
            taskListRes.setTaskLevel(String.valueOf(workflowTask.getDepth()));
            taskListRes.setSchedule(DateUtil.date2Str(workflowTask.getPlanStartTime(), DateFormatEnum.DAY) + "-" + DateUtil.date2Str(workflowTask.getPlanEndTime(), DateFormatEnum.DAY));
            taskListRes.setCompletion(iCompletion.getTaskCompletion(workflowTask.getId()));
            taskListRes.setStatus(dictCommonMapper.getName(DictCommonTypeEnum.TASK_STATUS.getValue(), workflowTask.getStatus()));
            taskList.add(taskListRes);
        }
        PageResults pageResult = CollectionUtils.getPageResult(taskList, taskSelectParam.getPageNo(), taskSelectParam.getPageSize());
        return pageResult;
    }

    @Override
    public TaskDetailsRes getTaskDetails(TaskDetailsParam taskDetailsParam) {
        WorkflowTask workflowTask = workflowTaskMapper.selectByPrimaryKey(taskDetailsParam.getTaskId());
        TaskDetailsRes taskDetailsRes = new TaskDetailsRes();
        taskDetailsRes.setProjectId(workflowTask.getProjectId());
        taskDetailsRes.setParentTaskId(workflowTask.getParentTaskId());
        taskDetailsRes.setWorkType(workflowTask.getType());
        taskDetailsRes.setTask(workflowTask.getName());
        taskDetailsRes.setDepartmentId(workflowTask.getDepartmentId());
        taskDetailsRes.setTaskStatus(workflowTask.getStatus());
        taskDetailsRes.setPlanStartTime(DateUtil.date2Str(workflowTask.getPlanStartTime(), DateFormatEnum.DAY));
        taskDetailsRes.setPlanEndTime(DateUtil.date2Str(workflowTask.getPlanEndTime(), DateFormatEnum.DAY));
        taskDetailsRes.setActualStartTime(workflowTask.getActualStartTime() != null ? DateUtil.date2Str(workflowTask.getActualStartTime(), DateFormatEnum.DAY) : null);
        taskDetailsRes.setActualEndTime(workflowTask.getActualEndTime() != null ? DateUtil.date2Str(workflowTask.getActualEndTime(), DateFormatEnum.DAY) : null);
        taskDetailsRes.setUrgency(workflowTask.getUrgency());
        taskDetailsRes.setDifficuly(workflowTask.getDifficult());
        taskDetailsRes.setWorkload(workflowTask.getWorkload());

        List<WorkflowTask> workflowTasks = workflowTaskMapper.getChildTasksById(taskDetailsParam.getTaskId());
        TimeRes time = workflowTaskMapper.getChildTimeById(taskDetailsParam.getTaskId());
        Date minDate = null;
        if (time != null) {
            if(time.getMaxActualEndTime() == null){
                time.setMaxActualEndTime(time.getMaxPlanEndTime());
            }
            try {
                minDate = DateUtil.str2Date(DateFormatEnum.DAY, time.getMinPlanStartTime());
            } catch (ParseException e) {
                throw new UnifiedException(e);
            }
        }
        List<TaskGanntChart> taskGanntCharts = new ArrayList<>();
        for (WorkflowTask task : workflowTasks) {
            TaskGanntChart taskGanntChart = new TaskGanntChart();
            taskGanntChart.setTaskName(workflowTask.getName());
            taskGanntChart.setChargeName(entityEmployeeMapper.getEmployeeNameById(workflowTask.getReceiver()));
            taskGanntChart.setTaskLevel(String.valueOf(workflowTask.getUrgency()));
            taskGanntChart.setPlanStartTime(DateUtil.date2Str(task.getPlanStartTime(), DateFormatEnum.SECONDS));
            taskGanntChart.setPlanEndTime(DateUtil.date2Str(task.getPlanEndTime(), DateFormatEnum.SECONDS));
            taskGanntChart.setActualStartTime(DateUtil.date2Str(task.getActualStartTime(), DateFormatEnum.SECONDS));
            taskGanntChart.setActualEndTime(DateUtil.date2Str(task.getActualEndTime(), DateFormatEnum.SECONDS));
            //返回坐标点方便前段展示
            String day = DateUtil.getDaysOfDifference(minDate, task.getPlanStartTime());
            taskGanntChart.setPlanStartPoint(day);
            taskGanntChart.setPlanEndDistance(DateUtil.getDaysOfDifference(task.getPlanStartTime(), task.getPlanEndTime()));
            if (task.getActualStartTime() != null) {
                taskGanntChart.setActualStartPoint(DateUtil.getDaysOfDifference(minDate, task.getActualStartTime()));
            }
            if (task.getActualEndTime() != null) {
                taskGanntChart.setActualEndDistance(DateUtil.getDaysOfDifference(task.getActualStartTime(), task.getActualEndTime()));
            }
            taskGanntCharts.add(taskGanntChart);
        }
        //根据计划时间排序
        taskGanntCharts.sort((r1, r2) -> r1.getPlanStartTime().compareTo(r2.getPlanStartTime()));
        PageResults pageResult = CollectionUtils.getPageResult(taskGanntCharts, taskDetailsParam.getPageNo(), taskDetailsParam.getPageSize());
        taskDetailsRes.setPageResults(pageResult);
        if(time!=null){
            taskDetailsRes.setMaxTime(time.getMaxActualEndTime());
            taskDetailsRes.setMinTime(time.getMinPlanStartTime());
        }
        return taskDetailsRes;
    }

    @Override
    public ArrayList<TaskHistoryRes>  getTaskHistory(TaskHistorySelectParam param) {
        //任务id
        String taskId = param.getTaskId();
        //开始时间
        String searchStartTime = param.getSearchStartTime();
        //结束时间
        String searchEndTime = param.getSearchEndTime();
        Example example = new Example(WorkflowTaskEvent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskId", taskId);
        if (StringUtils.notEmpty(searchStartTime)) {
            try {
                criteria.andGreaterThanOrEqualTo("gmtCreate", DateUtil.str2Date(DateFormatEnum.SECONDS, searchStartTime));
            } catch (ParseException e) {
                throw new UnifiedException(e);
            }
        }
        if (StringUtils.notEmpty(searchEndTime)) {
            try {
                criteria.andLessThanOrEqualTo("gmtCreate", DateUtil.str2Date(DateFormatEnum.SECONDS, searchEndTime));
            } catch (ParseException e) {
                throw new UnifiedException(e);
            }
        }
        example.setOrderByClause("gmt_create desc");
        List<WorkflowTaskEvent> workflowTaskEvents = workflowTaskEventMapper.selectByExample(example);
        HashMap<String, ArrayList<TaskHistorySub>> hashMap = new HashMap<>();
        /**
         * 查询子任务Ids
         */
        Example subExample = new Example(WorkflowTask.class);
        subExample.createCriteria().andEqualTo("parentTaskId", taskId);
        List<WorkflowTask> subWorkflowTasks = workflowTaskMapper.selectByExample(subExample);
        ArrayList<String> subWorkflowTaskIds = new ArrayList<>();
        subWorkflowTasks.forEach(r -> {
            subWorkflowTaskIds.add(r.getId());
        });
        Example exampleEvent = new Example(WorkflowTaskEvent.class);
        Example.Criteria criteriaEvent = exampleEvent.createCriteria();
        //是否有子任务
        boolean isHaveChildrenTask = subWorkflowTaskIds.size() != 0;
        if (isHaveChildrenTask) {
            criteriaEvent.andIn("taskId", subWorkflowTaskIds);
            //只查询创建和终止事件
            criteriaEvent.andIn("type", Arrays.asList(ProjectEventEnum.CREATE.getValue(), ProjectEventEnum.TERMINATION.getValue()));
            List<WorkflowTaskEvent> workflowTaskEventsSub = workflowTaskEventMapper.selectByExample(exampleEvent);
            workflowTaskEventsSub.forEach(r -> {
                String taskIdSub = r.getTaskId();
                WorkflowTask workflowTask = workflowTaskMapper.selectByPrimaryKey(taskIdSub);
                //创建人
                String name = entityEmployeeMapper.getEmployeeNameById(workflowTask.getCreatedBy());
                //子任务名称
                String taskName = workflowTask.getName();
                //任务等级
                String level = workflowTask.getDepth()!=null? workflowTask.getDepth().toString():"";
                /**
                 * 事件类型
                 */
                String type = r.getType();
                String operator = "创建了";
                if (ProjectEventEnum.TERMINATION.getValue().equals(type)) {
                    operator = "终止了";
                }
                r.setDescription(name + operator + level + "级任务" + " " + taskName);
            });
            //组装数据
            workflowTaskEvents.addAll(workflowTaskEventsSub);
        }
        workflowTaskEvents.sort((r1, r2) -> r1.getGmtCreate().compareTo(r2.getGmtCreate()));
        for (WorkflowTaskEvent workflowTaskEvent : workflowTaskEvents) {
            Date gmtCreate = workflowTaskEvent.getGmtCreate();
            //创建时间
            String gmtCreateStr = DateUtil.date2Str(gmtCreate, DateFormatEnum.DAY);
            /**
             * 封装描述
             */
            String desc = workflowTaskEvent.getDescription();
            TaskHistorySub taskHistorySub = new TaskHistorySub();
            taskHistorySub.setContent(desc);
            taskHistorySub.setDate(gmtCreateStr);
            workflowTaskEvent.getDescription();
            //根据时间分组
            if (hashMap.get(gmtCreateStr) == null) {
                hashMap.put(gmtCreateStr, new ArrayList<TaskHistorySub>(Arrays.asList(taskHistorySub)));
            } else {
                hashMap.get(gmtCreateStr).add(taskHistorySub);
            }
        }
        /**
         * 封装数据给前端
         */
        ArrayList<TaskHistoryRes>  taskHistoryResList = new ArrayList<>();
        Set<String> days = hashMap.keySet();
        for (String day : days) {
            TaskHistoryRes taskHistoryRes = new TaskHistoryRes();
            taskHistoryRes.setDate(day);
            taskHistoryRes.setTaskHistorySubs(hashMap.get(day));
            taskHistoryResList.add(taskHistoryRes);
        }
        return taskHistoryResList;
    }
}
