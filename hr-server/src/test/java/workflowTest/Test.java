package workflowTest;

import com.handge.hr.common.utils.FileConfigurationUtils;
import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.entity.behavior.web.request.statistics.FootPrintParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.ProjectSelectParam;
import com.handge.hr.domain.entity.manage.web.response.archive.InformationRes;
import com.handge.hr.domain.entity.manage.web.response.workflow.ProjectListRes;
import com.handge.hr.domain.repository.mapper.*;
import com.handge.hr.domain.repository.pojo.*;
import com.handge.hr.manage.common.utils.UuidUtils;
import com.handge.hr.manage.service.api.archive.IEmployee;
import com.handge.hr.manage.service.api.archive.IExcelInformationImport;
import com.handge.hr.server.Application;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by MaJianfu on 2018/8/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class Test {

    @Autowired
    EntityJobPostMapper postMapper;

    @Autowired
    IEmployee iEmployee;
    
    @Autowired
    EntityEmployeeMapper entityEmployeeMapper;

    @Autowired
    BehaviorEntityDeviceBasicMapper deviceBasicMapper;

    @Autowired
    BehaviorTagPropertyMapper tagPropertyMapper;

    @Autowired
    BehaviorLibProfessionalAccomplishmentMapper libProfessionalAccomplishmentMapper;

    @Autowired
    IExcelInformationImport excelInformationImport;

    @Autowired
    DictCommonMapper dictCommonMapper;
    @Autowired
    WorkflowTaskMapper workflowTaskMapper;
    @Autowired
    WorkflowProjectMapper workflowProjectMapper;



    @org.junit.Test
    public void test(){
        int depth = workflowTaskMapper.getDepth("4");
        System.out.println("1");
        Integer integer = excelInformationImport.importAttendanceFlowRecords();
        EntityJobPost jobPost1=new EntityJobPost();
        List<EntityJobPost> entityJobPosts = postMapper.selectAll();
        System.out.println("1");
        List<Map<String,String>> maps = entityEmployeeMapper.getFootPrintDetails(new FootPrintParam());
        BehaviorTagProperty tagProperty = new BehaviorTagProperty();
        tagProperty.setProperty(null);
        List<BehaviorTagProperty> behaviorTagProperties = tagPropertyMapper.select(tagProperty);
        Map<String, Map<String, String>> staticIpDepartment = deviceBasicMapper.getStaticIpDepartment();
        EntityJobPost jobPost=new EntityJobPost();
        jobPost.setId(UuidUtils.getUUid());
        jobPost.setDescription("555");
        jobPost.setGmtCreate(new Date());
        jobPost.setName("5555");
        jobPost.setRemark("5555");
        postMapper.insertSelective(jobPost);
        System.out.println("1");
    }

    @org.junit.Test
    public void testEmployee(){
        ProjectSelectParam param=new ProjectSelectParam();
        List<ProjectListRes> projectList = workflowProjectMapper.getProjectList(param);
        System.out.println("1");
        InformationRes employeeDetails = iEmployee.getEmployeeDetails("3478ef3102ea4ebc8453eb460634c723");
        System.out.println("1");
    }


    @org.junit.Test
    public void testDelete() {
        List<String> list = new ArrayList<>();
        list.add("172.18.199.42");
        list.add("172.18.199.111");
        list.add("3");
        list.add("4");
        list.add("5");
        StringBuffer temp = new StringBuffer();
        //temp.append("(");
        for (int i = 0; i < list.size(); i++) {
            temp.append("'");
            temp.append(list.get(i));
            temp.append("'");
            if (i != (list.size() - 1)) {
                temp.append(",");
            }
        }
        //temp.append(")");
        System.out.println(temp.toString());
    }

    @org.junit.Test
    public void updateEmployee(){
        List<EntityEmployee> entityEmployees = entityEmployeeMapper.selectAll();
        Map<Integer, DictCommon> map = dictCommonMapper.getDictCommonMap();
        for(EntityEmployee e:entityEmployees){
            EntityEmployee employee = entityEmployeeMapper.selectByPrimaryKey(e.getId());
            if(StringUtils.notEmpty(e.getGender())){
                employee.setGender(map.get(Integer.parseInt(e.getGender())).getCode());
            }
            if(StringUtils.notEmpty(e.getJobStatus())){
                employee.setJobStatus(map.get(Integer.parseInt(e.getJobStatus())).getCode());
            }
            if(StringUtils.notEmpty(e.getMaritalStatus())){
                employee.setMaritalStatus(map.get(Integer.parseInt(e.getMaritalStatus())).getCode());
            }
            if(StringUtils.notEmpty(e.getChildrenStatus())){
                employee.setChildrenStatus(map.get(Integer.parseInt(e.getChildrenStatus())).getCode());
            }
            if(StringUtils.notEmpty(e.getEducation())){
                employee.setEducation(map.get(Integer.parseInt(e.getEducation())).getCode());
            }
            if(StringUtils.notEmpty(e.getPoliticalStatus())){
                employee.setPoliticalStatus(map.get(Integer.parseInt(e.getPoliticalStatus())).getCode());
            }
            entityEmployeeMapper.updateByPrimaryKeySelective(employee);
        }
    }

    @org.junit.Test
    public void fileTest(){
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setEncoding("UTF-8")
                                .setFileName("com/handge/hr/manage/template/file.properties"));
        try {
            Configuration config = builder.getConfiguration();
            String attendanceFlowRecord = config.getString("attendanceFlowRecord");
            String employeeInformation = config.getString("employeeInformation");
            String dictCommon = config.getString("dictCommon");
            System.out.println(attendanceFlowRecord);
            System.out.println(employeeInformation);
            System.out.println(dictCommon);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void configTest() throws FileNotFoundException {
        String str = FileConfigurationUtils.getConfig("dictCommon");
        System.out.println(str);
        FileInputStream fileInputStream=new FileInputStream(str);
        System.out.println("1");
    }

}
