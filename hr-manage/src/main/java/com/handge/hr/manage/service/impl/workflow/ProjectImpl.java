package com.handge.hr.manage.service.impl.workflow;

import com.github.pagehelper.PageHelper;
import com.handge.hr.auth.service.api.IMyToken;
import com.handge.hr.common.enumeration.base.DateFormatEnum;
import com.handge.hr.common.enumeration.manage.*;
import com.handge.hr.common.utils.*;
import com.handge.hr.domain.entity.base.web.request.PageParam;
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
import com.handge.hr.manage.service.api.workflow.IProject;
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
@Service
public class ProjectImpl implements IProject {

    @Autowired
    WorkflowProjectMapper workflowProjectMapper;
    @Autowired
    WorkflowProjectEventMapper workflowProjectEventMapper;
    @Autowired
    DictCommonMapper dictCommonMapper;
    @Autowired
    DictCommonTypeMapper dictCommonTypeMapper;
    @Autowired
    EntityEmployeeMapper entityEmployeeMapper;
    @Autowired
    WorkflowTaskMapper workflowTaskMapper;
    @Autowired
    EntityDepartmentMapper entityDepartmentMapper;
    @Autowired
    IMyToken myToken;
    @Autowired
    ICompletion iCompletion;
    @Autowired
    WorkflowQcMapper workflowQcMapper;
    @Autowired
    IPointCard iPointCard;
    @Autowired
    IEvent addEvent;
    @Autowired
    ActivitiServer activitiServer;

    ArrayList arrayListDesc = new ArrayList();

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addProject(ArrayList<AddProjectParam> addProjectParamList) {
        String createById = myToken.getUserInfo().getEmployee().getId();
        for (AddProjectParam projectParam : addProjectParamList) {
            //1.创建项目
            WorkflowProject project = new WorkflowProject();
            project.setId(UuidUtils.getUUid());
            project.setGmtCreate(new Date());
            project.setName(projectParam.getName());
            project.setCreatedBy(createById);
            try {
                if (StringUtils.notEmpty(projectParam.getPlanStartTime())) {
                    project.setPlanStartTime(DateUtil.str2Date(DateFormatEnum.SECONDS, projectParam.getPlanStartTime()));
                }
                if (StringUtils.notEmpty(projectParam.getPlanEndTime())) {
                    project.setPlanEndTime(DateUtil.str2Date(DateFormatEnum.SECONDS, projectParam.getPlanEndTime()));
                }
            } catch (ParseException e) {
                throw new UnifiedException(e);
            }
            project.setPrincipal(projectParam.getPrincipal());
            project.setStatus(ProjectStatusEnum.UNRECEIVED.getValue());
            project.setRemark(projectParam.getRemark());
            project.setDescription(projectParam.getDescription());
            if (StringUtils.notEmpty(projectParam.getType())) {
                project.setQcDepartment(workflowQcMapper.getDepartmentId(projectParam.getType()));
                String date = DateUtil.date2Str(new Date(), DateFormatEnum.DAYNEW);
                LinkedList<String> list = workflowProjectMapper.getNumber(date);
                String num = "";
                // 通过比较器来实现排序
                if (null == list || list.size() == 0) {
                    num = "001";
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
                    num = String.format("%03d", newNum);
                }
                project.setNumber(NumberTypeEnum.PROJECT.getValue() + date + num);
            }
            if (StringUtils.notEmpty(projectParam.getNumber())) {
                project.setNumberShow(projectParam.getNumber());
            } else {
                project.setNumberShow(project.getNumber());
            }
            project.setType(projectParam.getType());
            workflowProjectMapper.insertSelective(project);
            //2.项目事件记录
            String name = entityEmployeeMapper.getEmployeeNameById(project.getCreatedBy());
            String description = EventDescription.create(name, NumberTypeEnum.PROJECT.getDescription());
            addEvent.addProjectEvent(project.getId(), ProjectEventEnum.CREATE.getValue(), description);
        }
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int modifyProject(AddProjectParam modifyProjectParam) {
        //1.修改项目
        WorkflowProject project = workflowProjectMapper.selectByPrimaryKey(modifyProjectParam.getId());
        project.setGmtModified(new Date());
        ProjectEventRes eventRes = new ProjectEventRes();
        if (StringUtils.notEmpty(modifyProjectParam.getName()) && !modifyProjectParam.getName().equals(project.getName())) {
            eventRes.setBeforeModified(project.getName());
            project.setName(modifyProjectParam.getName());
            eventRes.setAfterModified(project.getName());
            addDesc(ProjectModifyEnum.NAME.getValue(), eventRes.getBeforeModified(), eventRes.getAfterModified());
        }
        if (StringUtils.notEmpty(modifyProjectParam.getType()) && !modifyProjectParam.getType().equals(project.getType())) {
            eventRes.setBeforeModified(project.getType());
            project.setType(modifyProjectParam.getType());
            eventRes.setAfterModified(project.getType());
            addDesc(ProjectModifyEnum.TYPE.getValue(), dictCommonMapper.getName(DictCommonTypeEnum.PROJECT_TYPE.getValue(), eventRes.getBeforeModified()), dictCommonMapper.getName(DictCommonTypeEnum.PROJECT_TYPE.getValue(), eventRes.getAfterModified()));
        }
        if (StringUtils.notEmpty(modifyProjectParam.getPrincipal()) && !modifyProjectParam.getPrincipal().equals(entityEmployeeMapper.getEmployeeNameById(project.getPrincipal()))) {
            eventRes.setBeforeModified(entityEmployeeMapper.getEmployeeNameById(project.getPrincipal()));
            project.setPrincipal(modifyProjectParam.getPrincipal());
            eventRes.setAfterModified(modifyProjectParam.getPrincipal());
            addDesc(ProjectModifyEnum.PRINCIPAL.getValue(), eventRes.getBeforeModified(), eventRes.getAfterModified());
        }
        try {
            int startCode = 0;
            int endCode = 10;
            if (StringUtils.notEmpty(modifyProjectParam.getPlanStartTime()) && !modifyProjectParam.getPlanStartTime().substring(startCode, endCode).equals(DateUtil.date2Str(project.getPlanStartTime(), DateFormatEnum.DAY))) {
                eventRes.setBeforeModified(DateUtil.date2Str(project.getPlanStartTime(), DateFormatEnum.DAYOLD));
                project.setPlanStartTime(DateUtil.str2Date(DateFormatEnum.SECONDS, modifyProjectParam.getPlanStartTime()));
                eventRes.setAfterModified(DateUtil.date2Str(project.getPlanStartTime(), DateFormatEnum.DAYOLD));
                addDesc(ProjectModifyEnum.PLANSTARTTIME.getValue(), eventRes.getBeforeModified(), eventRes.getAfterModified());
            }
            if (StringUtils.notEmpty(modifyProjectParam.getPlanEndTime()) && !modifyProjectParam.getPlanEndTime().substring(startCode, endCode).equals(DateUtil.date2Str(project.getPlanEndTime(), DateFormatEnum.DAY))) {
                eventRes.setBeforeModified(DateUtil.date2Str(project.getPlanEndTime(), DateFormatEnum.DAYOLD));
                project.setPlanEndTime(DateUtil.str2Date(DateFormatEnum.SECONDS, modifyProjectParam.getPlanEndTime()));
                eventRes.setAfterModified(DateUtil.date2Str(project.getPlanEndTime(), DateFormatEnum.DAYOLD));
                addDesc(ProjectModifyEnum.PLANENDTIME.getValue(), eventRes.getBeforeModified(), eventRes.getAfterModified());
            }
        } catch (ParseException e) {
            throw new UnifiedException("日期格式错误", ExceptionWrapperEnum.IllegalArgumentException);
        }
        if (StringUtils.notEmpty(modifyProjectParam.getDescription()) && !modifyProjectParam.getDescription().equals(project.getDescription())) {
            eventRes.setBeforeModified(project.getDescription());
            project.setDescription(modifyProjectParam.getDescription());
            eventRes.setAfterModified(project.getDescription());
            addDesc(ProjectModifyEnum.DESCRIPTION.getValue(), eventRes.getBeforeModified(), eventRes.getAfterModified());
        }
        if (StringUtils.notEmpty(modifyProjectParam.getRemark()) && !modifyProjectParam.getRemark().equals(project.getRemark())) {
            eventRes.setBeforeModified(project.getRemark());
            project.setRemark(modifyProjectParam.getRemark());
            eventRes.setAfterModified(project.getRemark());
            addDesc(ProjectModifyEnum.REMARK.getValue(), eventRes.getBeforeModified(), eventRes.getAfterModified());
        }
        if (StringUtils.notEmpty(modifyProjectParam.getNumber()) && !modifyProjectParam.getNumber().equals(project.getNumberShow())) {
            eventRes.setBeforeModified(project.getNumberShow());
            project.setNumberShow(modifyProjectParam.getNumber());
            eventRes.setAfterModified(project.getNumberShow());
            addDesc(ProjectModifyEnum.NUMBER.getValue(), eventRes.getBeforeModified(), eventRes.getAfterModified());
        }
        workflowProjectMapper.updateByPrimaryKeySelective(project);
        //2.项目事件记录
        String desc = CollectionUtils.listToString(this.arrayListDesc);
        String name = entityEmployeeMapper.getEmployeeNameById(myToken.getUserInfo().getEmployee().getId());
        addEvent.addProjectEvent(project.getId(), ProjectEventEnum.MODIFY.getValue(), name + desc);
        return 1;
    }

    private void addDesc(String name, String before, String after) {
        this.arrayListDesc.add("将" + name + "从" + before + "修改为" + after);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteProject(DeleteProjectParam deleteProjectParam) {
        String statusId = ProjectStatusEnum.UNRECEIVED.getValue();
        List<WorkflowProject> workflowProjectList = workflowProjectMapper.selectByIdList(deleteProjectParam.getIds());
        List<String> ids = new ArrayList<>();
        for (WorkflowProject project : workflowProjectList) {
            if (statusId.equals(project.getStatus())) {
                ids.add(project.getId());
            } else {
                throw new UnifiedException("状态不是未接收状态，无法删除", ExceptionWrapperEnum.Data_Outside_Special_Range);
            }
        }
        workflowProjectMapper.deleteByIdList(ids);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int receivedProject(List<ReceivedProjectParam> receivedProjectParamList) {
        String name = entityEmployeeMapper.getEmployeeNameById(myToken.getUserInfo().getEmployee().getId());
        for (ReceivedProjectParam receivedProjectParam : receivedProjectParamList) {
            WorkflowProject project = workflowProjectMapper.selectByPrimaryKey(receivedProjectParam.getId());
            String statusId = ProjectStatusEnum.UNRECEIVED.getValue();
            if (statusId.equals(project.getStatus())) {
                project.setStatus(ProjectStatusEnum.MAKING.getValue());
                project.setActualStartTime(new Date());
                workflowProjectMapper.updateByPrimaryKeySelective(project);
                //项目事件记录
                String description = EventDescription.receive(name, NumberTypeEnum.PROJECT.getDescription());
                addEvent.addProjectEvent(project.getId(), ProjectEventEnum.RECEIVE.getValue(), description);
            } else {
                throw new UnifiedException("状态不是未接收状态，无法确认", ExceptionWrapperEnum.Data_Outside_Special_Range);
            }
        }
        return 1;
    }

    @Override
    public int projectQcDistribution(List<QcDistributionParam> qcDistributionParamList) {
        String name = entityEmployeeMapper.getEmployeeNameById(myToken.getUserInfo().getEmployee().getId());
        for (QcDistributionParam qcDistributionParam : qcDistributionParamList) {
            WorkflowProject project = workflowProjectMapper.selectByPrimaryKey(qcDistributionParam.getId());
            if (BooleanEnum.NO.getValue().equals(qcDistributionParam.getIsQC())) {
                project.setIsSkipQc(true);
                //项目事件记录
                String description = EventDescription.skipEvaluate(name, NumberTypeEnum.PROJECT.getDescription());
                addEvent.addProjectEvent(project.getId(), ProjectEventEnum.REVIEW_SKIPPING.getValue(), description);
            } else {
                project.setQcPerson(qcDistributionParam.getQcPersonId());
            }
            workflowProjectMapper.updateByPrimaryKeySelective(project);
        }
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int terminatedProject(List<TerminatedProjectParam> terminatedProjectParamList) {
        String name = entityEmployeeMapper.getEmployeeNameById(myToken.getUserInfo().getEmployee().getId());
        for (TerminatedProjectParam terminatedProjectParam : terminatedProjectParamList) {
            WorkflowProject project = workflowProjectMapper.selectByPrimaryKey(terminatedProjectParam.getId());
            String makingValue = ProjectStatusEnum.MAKING.getValue();
            if (makingValue.equals(project.getStatus())) {
                project.setStatus(ProjectStatusEnum.TERMINATION.getValue());
                workflowProjectMapper.updateByPrimaryKeySelective(project);
                WorkflowTask task = new WorkflowTask();
                task.setProjectId(project.getId());
                List<WorkflowTask> list = workflowTaskMapper.select(task);
                String terminationValue = TaskStatusEnum.TERMINATION.getValue();
                String completedValue = TaskStatusEnum.COMPLETED.getValue();
                if (null != list && list.size() != 0) {
                    for (WorkflowTask workflowTask : list) {
                        boolean isStatus = terminationValue.equals(workflowTask.getStatus()) || completedValue.equals(workflowTask.getStatus());
                        String status = "";
                        if (!isStatus) {
                            status = activitiServer.cancelProcess(workflowTask.getId());
                        } else {
                            status = terminationValue;
                        }
                        workflowTask.setStatus(status);
                        workflowTaskMapper.updateByPrimaryKeySelective(workflowTask);
                        String taskDescription = EventDescription.terminateProject(name, NumberTypeEnum.TASK.getDescription());
                        addEvent.addTaskEvent(workflowTask.getId(), ProjectEventEnum.TERMINATION.getValue(), taskDescription);
                    }
                }
                //项目事件记录
                String projectDescription = EventDescription.terminate(name, terminatedProjectParam.getReason(), NumberTypeEnum.PROJECT.getDescription());
                addEvent.addProjectEvent(project.getId(), ProjectEventEnum.TERMINATION.getValue(), projectDescription);
            } else {
                throw new UnifiedException("状态是未接收状态，无法终止", ExceptionWrapperEnum.Data_Outside_Special_Range);
            }
        }
        return 1;
    }

    @Override
    public int evaluateProject(ProjectEvaluateParam projectEvaluateParam) {
        WorkflowProject project = workflowProjectMapper.selectByPrimaryKey(projectEvaluateParam.getProjectId());
        project.setStatus(ProjectStatusEnum.COMPLETED.getValue());
        workflowProjectMapper.updateByPrimaryKeySelective(project);
        //项目事件记录(评价)
        String name = entityEmployeeMapper.getEmployeeNameById(myToken.getUserInfo().getEmployee().getId());
        String evaluateDescription = EventDescription.evaluate(name, NumberTypeEnum.PROJECT.getDescription());
        addEvent.addProjectEvent(project.getId(), ProjectEventEnum.EVALUATE.getValue(), evaluateDescription);
        //项目事件记录(结束)
        String endDescription = EventDescription.end(name, NumberTypeEnum.PROJECT.getDescription());
        addEvent.addProjectEvent(project.getId(), ProjectEventEnum.END.getValue(), endDescription);
        iPointCard.addPointCard(projectEvaluateParam.getAddPointCardParam());
        return 1;
    }

    @Override
    public PageResults<ProjectListRes> getProjectList(ProjectSelectParam projectSelectParam) {
        PageHelper.startPage(projectSelectParam.getPageNo(), projectSelectParam.getPageSize());
        List<ProjectListRes> projectList = workflowProjectMapper.getProjectList(projectSelectParam);
        //封装返回结果
        for (ProjectListRes pro : projectList) {
            pro.setProjectCompletePercent(iCompletion.getProjectCompletion(pro.getProjectId()));
            pro.setProjectStatus(dictCommonMapper.getName(DictCommonTypeEnum.PROJECT_STATUS.getValue(), pro.getProjectStatus()));
        }
        PageResults<ProjectListRes> pageInfo = PageUtils.getPageInfo(projectList);
        return pageInfo;
    }

    @Override
    public ProjectGanttChart getProjectsGanttChart(PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<ProjectGanttChartRes> workflowProjects = workflowProjectMapper.selectAllProject();
        ProjectGanttChart projectGanttChart = new ProjectGanttChart();
        TimeRes time = workflowProjectMapper.getTime();
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

        for (ProjectGanttChartRes projectGanttChartRes : workflowProjects) {
            projectGanttChartRes.setProjectLeader(entityEmployeeMapper.getEmployeeNameById(projectGanttChartRes.getProjectLeader()));
            projectGanttChartRes.setStatus(dictCommonMapper.getName(DictCommonTypeEnum.TASK_STATUS.getValue(), projectGanttChartRes.getStatus()));
            //返回坐标点方便前段展示
            try {
                projectGanttChartRes.setPlanStartPoint(DateUtil.getDaysOfDifference(minDate, DateUtil.str2Date(DateFormatEnum.DAY, projectGanttChartRes.getPlanStartTime())));
                projectGanttChartRes.setPlanEndDistance(DateUtil.getDaysOfDifference(DateUtil.str2Date(DateFormatEnum.DAY, projectGanttChartRes.getPlanStartTime()), DateUtil.str2Date(DateFormatEnum.DAY, projectGanttChartRes.getPlanEndTime())));
                if (projectGanttChartRes.getActualStartTime() != null) {
                    projectGanttChartRes.setActualStartPoint(DateUtil.getDaysOfDifference(minDate, DateUtil.str2Date(DateFormatEnum.DAY, projectGanttChartRes.getActualStartTime())));
                    if (projectGanttChartRes.getActualEndTime() != null) {
                        projectGanttChartRes.setActualEndDistance(DateUtil.getDaysOfDifference(DateUtil.str2Date(DateFormatEnum.DAY, projectGanttChartRes.getActualStartTime()), DateUtil.str2Date(DateFormatEnum.DAY, projectGanttChartRes.getActualEndTime())));
                    }
                }
            } catch (ParseException e) {
                throw new UnifiedException(e);
            }
        }
        workflowProjects.sort((r1, r2) -> r1.getPlanStartTime().compareTo(r2.getPlanStartTime()));
        PageResults<ProjectGanttChartRes> pageInfo = PageUtils.getPageInfo(workflowProjects);
        if(time!=null){
            projectGanttChart.setMinStartTime(time.getMinPlanStartTime());
            projectGanttChart.setMaxEndTime(time.getMaxActualEndTime());
        }
        projectGanttChart.setPageResults(pageInfo);
        return projectGanttChart;
    }


    @Override
    public ProjectDetailsRes getProjectDetails(ProjectDetailsParam projectDetailsParam) {
        WorkflowProject workflowProject = workflowProjectMapper.selectByPrimaryKey(projectDetailsParam.getProjectId());
        ProjectDetailsRes projectDetailsRes = new ProjectDetailsRes();
        projectDetailsRes.setName(workflowProject.getName());
        projectDetailsRes.setType(String.valueOf(workflowProject.getType()));
        projectDetailsRes.setDescription(workflowProject.getDescription());
        projectDetailsRes.setPrincipal(workflowProject.getPrincipal());
        projectDetailsRes.setStatus(dictCommonMapper.getName(DictCommonTypeEnum.TASK_STATUS.getValue(), workflowProject.getStatus()));
        projectDetailsRes.setPlanStartTime(DateUtil.date2Str(workflowProject.getPlanStartTime(), DateFormatEnum.SECONDS));
        projectDetailsRes.setPlanEndTime(DateUtil.date2Str(workflowProject.getPlanEndTime(), DateFormatEnum.SECONDS));
        projectDetailsRes.setRemark(workflowProject.getRemark());
        projectDetailsRes.setNumber(workflowProject.getNumberShow());
        projectDetailsRes.setActualStartTime(workflowProject.getActualStartTime() != null ? DateUtil.date2Str(workflowProject.getActualStartTime(), DateFormatEnum.SECONDS) : null);
        projectDetailsRes.setActualEndTime(workflowProject.getActualEndTime() != null ? DateUtil.date2Str(workflowProject.getActualEndTime(), DateFormatEnum.SECONDS) : null);
        //甘特图
        Example example = new Example(WorkflowTask.class);
        example.createCriteria().andEqualTo("projectId", projectDetailsParam.getProjectId());
        example.setOrderByClause("plan_start_time");
        List<WorkflowTask> workflowTasks = workflowTaskMapper.selectByExample(example);
        List<TaskGanntChart> taskGanntCharts = new ArrayList<>();
        TimeRes time = workflowTaskMapper.getTime(projectDetailsParam.getProjectId());
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
        for (WorkflowTask workflowTask : workflowTasks) {
            TaskGanntChart taskGanntChart = new TaskGanntChart();
            taskGanntChart.setTaskName(workflowTask.getName());
            taskGanntChart.setTaskChargeDepartment(entityDepartmentMapper.getDepartmentNameById(workflowTask.getDepartmentId()));
            taskGanntChart.setTaskLevel(String.valueOf(workflowTask.getUrgency()));
            taskGanntChart.setPlanStartTime(DateUtil.date2Str(workflowTask.getPlanStartTime(), DateFormatEnum.DAY));
            taskGanntChart.setPlanEndTime(DateUtil.date2Str(workflowTask.getPlanEndTime(), DateFormatEnum.DAY));
            taskGanntChart.setActualStartTime(workflowTask.getActualStartTime() != null ? DateUtil.date2Str(workflowTask.getActualStartTime(), DateFormatEnum.DAY) : null);
            taskGanntChart.setActualEndTime(workflowTask.getActualEndTime() != null ? DateUtil.date2Str(workflowTask.getActualEndTime(), DateFormatEnum.DAY) : null);
            //返回坐标点方便前段展示
            String day = DateUtil.getDaysOfDifference(minDate, workflowTask.getPlanStartTime());
            taskGanntChart.setPlanStartPoint(day);
            taskGanntChart.setPlanEndDistance(DateUtil.getDaysOfDifference(workflowTask.getPlanStartTime(), workflowTask.getPlanEndTime()));
            if (workflowTask.getActualStartTime() != null) {
                taskGanntChart.setActualStartPoint(DateUtil.getDaysOfDifference(minDate, workflowTask.getActualStartTime()));
            }
            if (workflowTask.getActualEndTime() != null) {
                taskGanntChart.setActualEndDistance(DateUtil.getDaysOfDifference(workflowTask.getActualStartTime(), workflowTask.getActualEndTime()));
            }
            taskGanntCharts.add(taskGanntChart);
        }
        //根据计划时间排序
        taskGanntCharts.sort((r1, r2) -> r1.getPlanStartTime().compareTo(r2.getPlanStartTime()));
        PageResults pageResult = CollectionUtils.getPageResult(taskGanntCharts, projectDetailsParam.getPageNo(), projectDetailsParam.getPageSize());
        projectDetailsRes.setTaskGanntCharts(pageResult);
        if(time!=null){
            projectDetailsRes.setMaxTime(time.getMaxActualEndTime());
            projectDetailsRes.setMinTime(time.getMinPlanStartTime());
        }
        return projectDetailsRes;
    }

    @Override
    public ArrayList<ProjectHistoryRes> getProjectHistory(ProjectHistorySelectParam param) {
        //项目id
        String projectId = param.getProjectId();
        //开始时间
        String searchStartTime = param.getSearchStartTime();
        //结束时间
        String searchEndTime = param.getSearchEndTime();
        Example example = new Example(WorkflowProjectEvent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("projectId", projectId);
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
        List<WorkflowProjectEvent> workflowProjectEvents = workflowProjectEventMapper.selectByExample(example);
        HashMap<String, ArrayList<ProjectHistorySub>> hashMap = new HashMap<>();
        for (WorkflowProjectEvent workflowProjectEvent : workflowProjectEvents) {
            Date gmtCreate = workflowProjectEvent.getGmtCreate();
            //创建时间
            String gmtCreateStr = DateUtil.date2Str(gmtCreate, DateFormatEnum.DAY);
            /**
             * 封装描述
             */
            String desc = workflowProjectEvent.getDescription();
            ProjectHistorySub projectHistorySub = new ProjectHistorySub();
            projectHistorySub.setContent(desc);
            projectHistorySub.setDate(DateUtil.date2Str(gmtCreate, DateFormatEnum.SECONDS));
            workflowProjectEvent.getDescription();
            if (hashMap.get(gmtCreateStr) == null) {
                hashMap.put(gmtCreateStr, new ArrayList<ProjectHistorySub>(Arrays.asList(projectHistorySub)));
            } else {
                hashMap.get(gmtCreateStr).add(projectHistorySub);
            }
        }
        ArrayList<ProjectHistoryRes> projectHistoryList = new ArrayList<>();
        for (String date : hashMap.keySet()) {
            ProjectHistoryRes projectHistoryRes = new ProjectHistoryRes();
            projectHistoryRes.setDate(date);
            projectHistoryRes.setProjectHistorySubs(hashMap.get(date));
            projectHistoryList.add(projectHistoryRes);
        }

        return projectHistoryList;
    }

}
