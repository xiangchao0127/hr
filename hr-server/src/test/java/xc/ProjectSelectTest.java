package xc;

import com.handge.hr.common.utils.PageResults;
import com.handge.hr.domain.entity.base.web.request.PageParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.*;
import com.handge.hr.domain.entity.manage.web.response.archive.DictCommonData;
import com.handge.hr.domain.entity.manage.web.response.workflow.*;
import com.handge.hr.domain.repository.mapper.DictCommonMapper;
import com.handge.hr.domain.repository.mapper.EntityEmployeeMapper;
import com.handge.hr.manage.service.api.workflow.IProject;
import com.handge.hr.manage.service.api.workflow.ITask;
import com.handge.hr.server.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@SuppressWarnings("ALL")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ProjectSelectTest {
    @Autowired
    IProject projectSelect;
    @Autowired
    DictCommonMapper dictCommonMapper;
    @Autowired
    EntityEmployeeMapper entityEmployeeMapper;
    @Autowired
    ITask iTask;


    /**
     * 项目列表
     */
    @Test
    public void getProjectList() {
        ProjectSelectParam projectSelectParam = new ProjectSelectParam();
        /*projectSelectParam.setPageNo(0);
        projectSelectParam.setPageSize(10);
        projectSelectParam.setProjectNumber("A20180821001");
        projectSelectParam.setPageNo(2);*/
        PageResults<ProjectListRes> projectList = projectSelect.getProjectList(projectSelectParam);
        System.out.println(projectList);
    }

    /**
     * 项目列表(甘特图)
     */
    @Test
    public void getProjectsGanttChart() {
        PageParam pageParam = new PageParam();
        pageParam.setPageNo(0);
        pageParam.setPageSize(10);
        ProjectGanttChart projectsGanttChart = projectSelect.getProjectsGanttChart(pageParam);
        System.out.println(projectsGanttChart);
    }

    /**
     * 项目详情
     */
    @Test
    public void getProjectDetails() {
        ProjectDetailsParam param = new ProjectDetailsParam();
        param.setProjectId("f0c3b6f8df474d01b479b8d2e99e029f");
        ProjectDetailsRes projectDetailsRes = projectSelect.getProjectDetails(param);
        System.out.println(projectDetailsRes);
    }


    /**
     * 获取任务列表
     */
    @Test
    public void getTaskList() {
        TaskSelectParam taskSelectParam = new TaskSelectParam();
        taskSelectParam.setPageNo(1);
        taskSelectParam.setPageSize(10);
        PageResults<TaskListRes> taskList = iTask.getTaskList(taskSelectParam);
        System.out.println(taskList);
    }

    /**
     * 获取任务详情
     */
    @Test
    public void getTaskDetails() {
        TaskDetailsParam taskDetailsParam = new TaskDetailsParam();
        taskDetailsParam.setTaskId("1");
        TaskDetailsRes taskDetails = iTask.getTaskDetails(taskDetailsParam);
        System.out.println(taskDetails);
    }


}
