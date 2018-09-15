package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.entity.manage.web.response.workflow.TimeRes;
import com.handge.hr.domain.repository.pojo.WorkflowTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.code.ORDER;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by DaLu Guo on 2018/8/14.
 */
@Mapper
public interface WorkflowTaskMapper extends CommonMapper<WorkflowTask, String> {
    @Select("WITH RECURSIVE r AS (\n" +
            " \n" +
            "       SELECT * FROM workflow_task WHERE id = #{taskId}\n" +
            " \n" +
            "     union   ALL\n" +
            " \n" +
            "       SELECT workflow_task.* FROM workflow_task, r WHERE workflow_task.parent_task_id = r.id\n" +
            " \n" +
            "     )\n" +
            "\n" +
            "SELECT * FROM r ORDER BY plan_start_time;")
    List<WorkflowTask> getChildTasksById(String taskId);

    @Select("WITH RECURSIVE r AS (\n" +
            "\tSELECT\n" +
            "\t\t*\n" +
            "\tFROM\n" +
            "\t\tworkflow_task\n" +
            "\tWHERE\n" +
            "\t\tID = #{taskId}\n" +
            "\tUNION ALL\n" +
            "\t\tSELECT\n" +
            "\t\t\tworkflow_task.*\n" +
            "\t\tFROM\n" +
            "\t\t\tworkflow_task,\n" +
            "\t\t\tr\n" +
            "\t\tWHERE\n" +
            "\t\t\tworkflow_task.parent_task_id = r. ID\n" +
            ") SELECT\n" +
            "\tMIN (plan_start_time) AS min_plan_start_time,\n" +
            "\tMAX (plan_end_time) AS max_plan_end_time,\n" +
            "\tMAX (actual_end_time) AS max_actual_end_time\n" +
            "FROM\n" +
            "\tr;")
    TimeRes getChildTimeById(String taskId);

    @Select("select number from workflow_task WHERE number like '%${date}%' ")
    LinkedList<String> getNumber(@Param("date") String date);

    @Select("select repeat from workflow_task where id=#{taskId}")
    int getRepeat(String taskId);
    @Select("select depth from workflow_task where id=#{taskId}")
    int getDepth(String taskId);
    @Select("select department_id from workflow_task where id =#{taskId}")
    String getDepartmentById(String taskId);

    @Select("select id from workflow_task where parent_task_id=#{parentTaskId}")
    List<String> getTaskIdByParentTaskId(String parentTaskId);
    /**
     * 获取任务最大最小时间(计划，实际)
     */
    @Select("select min(plan_start_time) as min_plan_start_time,max(plan_end_time) as max_plan_end_time,max(actual_end_time) as max_actual_end_time from workflow_task where project_id = #{projectId}")
    TimeRes getTime(String projectId);

}
