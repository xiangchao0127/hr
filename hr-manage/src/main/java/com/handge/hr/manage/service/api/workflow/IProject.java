package com.handge.hr.manage.service.api.workflow;

import com.handge.hr.common.utils.PageResults;
import com.handge.hr.domain.entity.base.web.request.PageParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.*;
import com.handge.hr.domain.entity.manage.web.response.workflow.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaJianfu on 2018/8/16.
 */
public interface IProject {

    /**
     * 创建项目
     */
    int addProject(ArrayList<AddProjectParam> addProjectParamList);

    /**
     * 修改项目
     */
    int modifyProject(AddProjectParam modifyProjectParam);

    /**
     * 删除项目
     */
    int deleteProject(DeleteProjectParam deleteProjectParam);

    /**
     * 接收项目
     */
    int receivedProject(List<ReceivedProjectParam> receivedProjectParamList);

    /**
     * QC分配
     */
    int projectQcDistribution(List<QcDistributionParam> qcDistributionParamList);

    /**
     * 终止项目
     */
    int terminatedProject(List<TerminatedProjectParam> terminatedProjectParamList);

    /**
     * 评价项目
     */
    int evaluateProject(ProjectEvaluateParam projectEvaluateParam);

    /**
     * 项目列表
     */
    PageResults<ProjectListRes> getProjectList(ProjectSelectParam projectSelectParam);

    /**
     * 项目总览(甘特图)
     */
    ProjectGanttChart getProjectsGanttChart(PageParam pageParam);

    /**
     * 项目详情
     */
    ProjectDetailsRes getProjectDetails(ProjectDetailsParam projectDetailsParam);

    /**
     * 获取项目历史记录
     */
    ArrayList<ProjectHistoryRes> getProjectHistory(ProjectHistorySelectParam param);


}
