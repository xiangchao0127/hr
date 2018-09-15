package com.handge.hr.manage.service.api.workflow;

import com.handge.hr.domain.entity.manage.web.request.workflow.EmployeeByRoleNameParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.DictCommonTypeParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.TaskDetailsParam;
import com.handge.hr.domain.entity.manage.web.response.archive.CommonData;
import com.handge.hr.domain.entity.manage.web.response.archive.DictCommonData;
import com.handge.hr.domain.entity.manage.web.response.archive.EmployeeSampleRes;
import com.handge.hr.domain.repository.pojo.DictCommonType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by xc in 2018/08/31
 */
public interface IDropDown {
    /**
     * 数据字典类型查询
     */
    List<DictCommonType> getDictCommonType();

    /**
     * 数据字典查询
     */
    List<DictCommonData> getDictCommonData(DictCommonTypeParam dictCommonTypeParam);

    /**
     * 查询字典表所有数据
     */
    Map<String, List<CommonData>> getAllDictCommonData();

    /**
     * 员工查询根据角色名称
     */
    List<EmployeeSampleRes> getEmployeeByRoleName(EmployeeByRoleNameParam employeeByRoleNameParam);

    /**
     * 获取项目名称列表
     */
    List<DictCommonData> getProjectNameList();

    /**
     * 获取项目编号列表
     */
    List<DictCommonData> getProjectNumberList();

    /**
     * 获取部门列表
     */
    List<DictCommonData> getDepartmentList();

    /**
     * 获取部门以及部门所对应的员工
     */
    HashMap<String, ArrayList<DictCommonData>> getDepartmentAndEmployees();

    /**
     * 根据任务id获取QC人员列表
     */
    HashMap<String, ArrayList<DictCommonData>> getQcEmployees(TaskDetailsParam taskDetailsParam);

    /**
     * 根据项目经理角色查询员工
     */
    List<EmployeeSampleRes> getEmployeesByManager();

}
