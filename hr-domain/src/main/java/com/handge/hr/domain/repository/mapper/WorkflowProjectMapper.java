package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.entity.manage.web.response.workflow.ProjectGanttChartRes;
import com.handge.hr.domain.entity.manage.web.response.workflow.TimeRes;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.entity.manage.web.request.workflow.ProjectSelectParam;
import com.handge.hr.domain.entity.manage.web.response.workflow.ProjectListRes;
import com.handge.hr.domain.repository.pojo.WorkflowProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by DaLu Guo on 2018/8/14.
 */
@Mapper
public interface WorkflowProjectMapper extends CommonMapper<WorkflowProject, String> {

    @Select("select number from workflow_project WHERE number like '%${date}%' ")
    LinkedList<String> getNumber(@Param("date") String date);

    /**
     * 根据项目名称获取id
     *
     * @param name 项目名称
     * @return 项目id
     */
    @Select("select id from workflow_project WHERE name = #{name} ")
    String getProjectIdByName(String name);

    /**
     * 获取项目最大最小时间(计划，实际)
     *
     * @return
     */
    @Select("select min(plan_start_time) as min_plan_start_time,max(plan_end_time) as max_plan_end_time,max(actual_end_time) as max_actual_end_time from workflow_project")
    TimeRes getTime();

    @Select("SELECT\n" +
            "\tname project_name,\n" +
            "\tprincipal project_leader,\n" +
            "\tstatus,\n" +
            "\tplan_start_time,\n" +
            "\tplan_end_time,\n" +
            "\tworkflow_project.actual_start_time,\n" +
            "\tworkflow_project.actual_end_time\n" +
            "FROM\n" +
            "\tworkflow_project")
    List<ProjectGanttChartRes> selectAllProject();

    @SelectProvider(type = ProjectSqlBuilder.class, method = "buildGetProject")
    List<ProjectListRes> getProjectList(ProjectSelectParam projectSelectParam);

    class ProjectSqlBuilder {
        public static String buildGetProject(ProjectSelectParam projectSelectParam) {
            return new SQL() {{
                SELECT("ROW_NUMBER () OVER (ORDER BY pro.gmt_create DESC) AS orderNumber,pro.id AS projectId,pro.number_show " +
                        "AS projectNumber,pro. name AS projectName,emp. name AS projectPrincipal,pro.plan_start_time AS projectStartTime," +
                        "pro.plan_end_time AS projectEndTime,pro.status AS projectStatus");
                FROM("workflow_project pro");
                JOIN("entity_employee emp ON pro.principal = emp.id");
                WHERE("1=1");
                if (StringUtils.notEmpty(projectSelectParam.getProjectNumber())) {
                    AND();
                    WHERE("pro.number_show like '%" + projectSelectParam.getProjectNumber() + "%'");
                }
                if (StringUtils.notEmpty(projectSelectParam.getProjectName())) {
                    AND();
                    WHERE("pro.name like '%" + projectSelectParam.getProjectName() + "%'");
                }
                if (StringUtils.notEmpty(projectSelectParam.getCreateName())) {
                    AND();
                    WHERE("emp.name like '%" + projectSelectParam.getCreateName() + "%'");
                }
                if (StringUtils.notEmpty(projectSelectParam.getProjectStatus())) {
                    AND();
                    WHERE("pro.status ='" + projectSelectParam.getProjectStatus() + "'");
                }
                if (StringUtils.notEmpty(projectSelectParam.getStartTime())) {
                    AND();
                    WHERE("pro.plan_start_time >= '" + projectSelectParam.getStartTime() + "'::date");
                }
                if (StringUtils.notEmpty(projectSelectParam.getEndTime())) {
                    AND();
                    WHERE("'" + projectSelectParam.getEndTime() + "' ::date <=pro.plan_end_time");
                }
            }}.toString();
        }
    }
}
