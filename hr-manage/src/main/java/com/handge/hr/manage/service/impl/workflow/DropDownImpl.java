package com.handge.hr.manage.service.impl.workflow;

import com.handge.hr.common.enumeration.manage.JobStatusEnum;
import com.handge.hr.common.utils.CollectionUtils;
import com.handge.hr.domain.entity.manage.web.request.workflow.EmployeeByRoleNameParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.DictCommonTypeParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.TaskDetailsParam;
import com.handge.hr.domain.entity.manage.web.response.archive.CommonData;
import com.handge.hr.domain.entity.manage.web.response.archive.DictCommonData;
import com.handge.hr.domain.entity.manage.web.response.archive.EmployeeSampleRes;
import com.handge.hr.domain.repository.mapper.*;
import com.handge.hr.domain.repository.pojo.*;
import com.handge.hr.manage.service.api.workflow.IDropDown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * create by xc in 2018/08/31
 */
@Component
public class DropDownImpl implements IDropDown {
    @Autowired
    WorkflowTaskMapper workflowTaskMapper;
    @Autowired
    DictCommonTypeMapper dictCommonTypeMapper;
    @Autowired
    WorkflowProjectMapper workflowProjectMapper;
    @Autowired
    DictCommonMapper dictCommonMapper;
    @Autowired
    EntityEmployeeMapper entityEmployeeMapper;
    @Autowired
    EntityDepartmentMapper entityDepartmentMapper;


    @Override
    public List<DictCommonType> getDictCommonType() {
        List<DictCommonType> dictCommonTypes = dictCommonTypeMapper.selectAll();
        return dictCommonTypes;
    }

    @Override
    public List<DictCommonData> getDictCommonData(DictCommonTypeParam dictCommonTypeParam) {
        List<DictCommonData> dictCommonDatas = dictCommonMapper.getNames(dictCommonTypeParam.getNameEn());
        dictCommonDatas.sort((r1, r2) -> ((String) r1.getId()).compareTo((String) r2.getId()));
        return dictCommonDatas;
    }

    @Override
    public Map<String, List<CommonData>> getAllDictCommonData() {
        List<CommonData> commonDatas = dictCommonMapper.getAll();
        Map<String, List<CommonData>> groupByEn = CollectionUtils.group(commonDatas, r -> ((CommonData) r).getNameEn());
        return groupByEn;
    }

    @Override
    public List<EmployeeSampleRes> getEmployeeByRoleName(EmployeeByRoleNameParam employeeByRoleNameParam) {
        List<EmployeeSampleRes> employeesByRoleName = entityEmployeeMapper.getEmployeesByRoleName(employeeByRoleNameParam.getRoleName());
        return employeesByRoleName;
    }

    @Override
    public List<DictCommonData> getProjectNameList() {
        List<WorkflowProject> workflowProjects = workflowProjectMapper.selectAll();
        List<DictCommonData> projects = new ArrayList<>();
        workflowProjects.forEach(r -> {
            DictCommonData dictCommonData = new DictCommonData();
            dictCommonData.setId(r.getId());
            dictCommonData.setName(r.getName());
            projects.add(dictCommonData);
        });
        return projects;
    }

    @Override
    public List<DictCommonData> getProjectNumberList() {
        List<WorkflowProject> workflowProjects = workflowProjectMapper.selectAll();
        List<DictCommonData> projects = new ArrayList<>();
        workflowProjects.forEach(r -> {
            DictCommonData dictCommonData = new DictCommonData();
            dictCommonData.setId(r.getId());
            dictCommonData.setName(r.getNumberShow());
            projects.add(dictCommonData);
        });
        return projects;
    }

    @Override
    public List<DictCommonData> getDepartmentList() {
        List<EntityDepartment> entityDepartments = entityDepartmentMapper.selectAll();
        List<DictCommonData> departments = new ArrayList<>();
        entityDepartments.forEach(r -> {
            DictCommonData dictCommonData = new DictCommonData();
            dictCommonData.setId(r.getId());
            dictCommonData.setName(r.getName());
            departments.add(dictCommonData);
        });
        return departments;
    }

    @Override
    public HashMap<String, ArrayList<DictCommonData>> getDepartmentAndEmployees() {
        Example example = new Example(EntityEmployee.class);
        example.createCriteria().andNotEqualTo("jobStatus", JobStatusEnum.DIMISSION.getValue());
        List<EntityEmployee> entityEmployees = entityEmployeeMapper.selectByExample(example);
        HashMap<String, ArrayList<DictCommonData>> employeeInfo = new HashMap<>();
        List<EntityDepartment> entityDepartments = entityDepartmentMapper.selectAll();
        HashMap<String, String> departmentHash = new HashMap<>();
        entityDepartments.forEach(r -> {
            departmentHash.put(r.getId(), r.getName());
        });
        entityEmployees.forEach(r -> {
            String departmentName = departmentHash.get(r.getDepartmentId());
            DictCommonData dictCommonData = new DictCommonData();
            dictCommonData.setId(r.getJobNumber());
            dictCommonData.setName(r.getName());
            if (employeeInfo.get(departmentName) == null) {
                employeeInfo.put(departmentName, new ArrayList<>(Arrays.asList(dictCommonData)));
            } else {
                employeeInfo.get(departmentName).add(dictCommonData);
            }
        });
        return employeeInfo;
    }

    @Override
    public HashMap<String, ArrayList<DictCommonData>> getQcEmployees(TaskDetailsParam taskDetailsParam) {
        String qcPerson = workflowTaskMapper.selectByPrimaryKey(taskDetailsParam.getTaskId()).getQcPerson();
        String departmentId = entityEmployeeMapper.selectByPrimaryKey(qcPerson).getDepartmentId();
        Example example = new Example(EntityEmployee.class);
        example.createCriteria().andEqualTo("departmentId", departmentId);
        List<EntityEmployee> entityEmployees = entityEmployeeMapper.selectByExample(example);
        HashMap<String, ArrayList<DictCommonData>> hashMap = new HashMap<>();
        ArrayList<DictCommonData> dictCommonData = new ArrayList<>();
        entityEmployees.forEach(r -> {
            DictCommonData dictCommon = new DictCommonData();
            dictCommon.setId(r.getJobNumber());
            dictCommon.setName(r.getName());
            dictCommonData.add(dictCommon);
        });
        hashMap.put(entityDepartmentMapper.selectByPrimaryKey(departmentId).getName(), dictCommonData);
        return hashMap;
    }

    @Override
    public List<EmployeeSampleRes> getEmployeesByManager() {
        List<EmployeeSampleRes> employees = entityEmployeeMapper.getEmployeesByRoleName("项目经理");
        return employees;
    }
}
