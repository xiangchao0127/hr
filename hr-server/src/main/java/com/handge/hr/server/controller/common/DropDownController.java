package com.handge.hr.server.controller.common;

import com.handge.hr.domain.entity.manage.web.request.workflow.EmployeeByRoleNameParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.DictCommonTypeParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.TaskDetailsParam;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import com.handge.hr.manage.service.api.workflow.IDropDown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

/**
 * create by xc in 2018/09/03
 */
@RestController
@RequestMapping(value = "/drop_down", produces = "application/json")
public class DropDownController {

    @Autowired
    IDropDown dropDown;

    /**
     * 数据字典类型查询
     */
    @GetMapping("/get_dict_common_type")
    public ResponseEntity getDictCommonType() {
        return ResponseEntity.ok().body(dropDown.getDictCommonType());
    }

    /**
     * 按字典类型查询数据字典
     */
    @GetMapping("/get_dict_common")
    public ResponseEntity getDictCommonData(@Valid DictCommonTypeParam dictCommonTypeParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(dropDown.getDictCommonData(dictCommonTypeParam));
    }

    /**
     * 查询所有数据字典
     */
    @GetMapping("/get_all_dict_common")
    public ResponseEntity getAllDictCommonData() {
        return ResponseEntity.ok().body(dropDown.getAllDictCommonData());
    }

    /**
     * 按角色名称查询员工
     */
    @GetMapping("/get_employee_by_role")
    public ResponseEntity getEmployeeByRoleName(@Valid EmployeeByRoleNameParam employeeByRoleNameParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(dropDown.getEmployeeByRoleName(employeeByRoleNameParam));
    }

    /**
     * 获取所有项目名称列表
     */
    @GetMapping("/get_project_name")
    public ResponseEntity getProjectNameList() {
        return ResponseEntity.ok().body(dropDown.getProjectNameList());
    }

    /**
     * 获取项目编号列表
     */
    @GetMapping("/get_project_numbers")
    public ResponseEntity getProjectNumberList() {
        return ResponseEntity.ok().body(dropDown.getProjectNumberList());
    }

    /**
     * 获取部门列表
     */
    @GetMapping("/get_departments")
    public ResponseEntity getDepartmentList() {
        return ResponseEntity.ok().body(dropDown.getDepartmentList());
    }

    /**
     * 获取部门以及部门所对应的员工
     */
    @GetMapping("/get_department_and_employees")
    public ResponseEntity getDepartmentAndEmployees() {
        return ResponseEntity.ok().body(dropDown.getDepartmentAndEmployees());
    }

    /**
     * 根据任务id获取QC人员列表
     */
    @GetMapping("/get_qc_employees")
    public ResponseEntity getQcEmployees(@Valid TaskDetailsParam taskDetailsParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(dropDown.getQcEmployees(taskDetailsParam));
    }

    /**
     * 根据项目经理角色查询员工      查询角色为项目经理的员工？
     */
    @GetMapping("/get_employees_by_manager")
    public ResponseEntity getEmployeesByManager() {
        return ResponseEntity.ok().body(dropDown.getEmployeesByManager());
    }
}
