package xc;

import com.handge.hr.auth.service.api.IIdentification;
import com.handge.hr.common.utils.PageResults;
import com.handge.hr.domain.entity.manage.web.request.workflow.EmployeeByRoleNameParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.ProjectHistorySelectParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.DictCommonTypeParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.TaskHistorySelectParam;
import com.handge.hr.domain.entity.manage.web.response.archive.DictCommonData;
import com.handge.hr.domain.entity.manage.web.response.archive.EmployeeSampleRes;
import com.handge.hr.domain.entity.manage.web.response.workflow.ProjectHistoryRes;
import com.handge.hr.domain.entity.manage.web.response.workflow.TaskHistoryRes;
import com.handge.hr.domain.entity.manage.web.response.workflow.TimeRes;
import com.handge.hr.domain.repository.mapper.EmployeeDepartmentPostionViewMapper;
import com.handge.hr.domain.repository.mapper.WorkflowProjectMapper;
import com.handge.hr.domain.repository.pojo.DictCommonType;
import com.handge.hr.domain.repository.pojo.EmployeeDepartmentPostionView;
import com.handge.hr.manage.service.api.workflow.IDropDown;
import com.handge.hr.manage.service.api.workflow.IProject;
import com.handge.hr.manage.service.api.workflow.ITask;
import com.handge.hr.server.Application;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class viewTest {
    @Autowired
    EmployeeDepartmentPostionViewMapper employeeDepartmentPostionViewMapper;
    @Autowired
    IdentityService identityService;
    @Autowired
    IDropDown iDropDown;
    @Autowired
    IProject iProject;
    @Autowired
    ITask iTask;
    @Autowired
    IIdentification iIdentification;
    @Autowired
    WorkflowProjectMapper workflowProjectMapper;


    @Test
    public void testView() {
        Example example = new Example(EmployeeDepartmentPostionView.class);
        example.createCriteria().andEqualTo("departmentName", "开发部");
        List<EmployeeDepartmentPostionView> employeeDepartmentPostionViews = employeeDepartmentPostionViewMapper.selectByExample(example);
        List<EmployeeDepartmentPostionView> entity = employeeDepartmentPostionViewMapper.getEmployeeInfoByName("向超");
        System.out.println(employeeDepartmentPostionViews);
        System.out.println(entity);
    }

    @Test
    public void testIdentify() {
//        User user = identityService.newUser("Jonathan");
//        user.setFirstName("Jonathan");
//        user.setLastName("chang");
//        user.setEmail("whatlookingfor@gmail.com");
//        user.setPassword("123");
//        //保存用户到数据库
//        identityService.saveUser(user);
        //用户的查询
        User userInDb = identityService.createUserQuery().userId("c9645d1a085b4c7d9523b38c37cd2a46").singleResult();
        System.out.println(userInDb);
        Assert.notNull(userInDb);

//        //验证用户名和密码
//        Assert.isTrue(identityService.checkPassword("Jonathan","123"));
//
//        //删除用户
//        identityService.deleteUser("Jonathan");
//
//        //验证是否删除成功
//        userInDb = identityService.createUserQuery().userId("Jonathan").singleResult();
//        Assert.isNull(userInDb);
    }

    @Test
    public void test() {
        List<DictCommonData> departmentList = iDropDown.getDepartmentList();
        System.out.println(departmentList);
        List<DictCommonType> dictCommonType = iDropDown.getDictCommonType();
        System.out.println(dictCommonType);
        List<DictCommonData> projectNameList = iDropDown.getProjectNameList();
        System.out.println(projectNameList);
        List<DictCommonData> education = iDropDown.getDictCommonData(new DictCommonTypeParam() {{
            this.setNameEn("education");
        }});
        System.out.println(education);
        List<EmployeeSampleRes> 普通员工 = iDropDown.getEmployeeByRoleName(new EmployeeByRoleNameParam() {{
            this.setRoleName("普通员工");
        }});
        System.out.println(普通员工);
//        HashMap<String, ArrayList<DictCommonData>> departmentAndEmployees = iDropDown.getQcEmployees("c5f6f1a4f0264078bd5a0eb78063c2fa");
//        System.out.println(departmentAndEmployees);
        System.out.println("-------------");
        ArrayList<ProjectHistoryRes> c94dbd3577074d6982d63293dd7400e2 = iProject.getProjectHistory(new ProjectHistorySelectParam() {{
            this.setProjectId("c94dbd3577074d6982d63293dd7400e2");
        }});
        System.out.println(c94dbd3577074d6982d63293dd7400e2);
        ArrayList<TaskHistoryRes> taskHistory = iTask.getTaskHistory(new TaskHistorySelectParam() {{
            this.setTaskId("1");
        }});
        System.out.println(taskHistory);
    }

    @Test
    public void testLogin(){
//        LoginFormParam loginFormParam = new LoginFormParam();
//        loginFormParam.setUsername("xc");
//        loginFormParam.setPassword("111");
//        Object login = iIdentification.login(loginFormParam);
//        System.out.println(login);
//        System.out.println(iDropDown.getEmployeesByManager());
        TimeRes minPlanStartTime = workflowProjectMapper.getTime();
        System.out.println(minPlanStartTime);
    }


}
