package com.handge.hr.server.controller.manage;

import com.handge.hr.domain.entity.manage.web.request.archive.DeleteParam;
import com.handge.hr.domain.entity.manage.web.request.archive.DeleteParams;
import com.handge.hr.domain.entity.manage.web.request.employee.EmployeeParam;
import com.handge.hr.domain.entity.manage.web.request.archive.InformationParam;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import com.handge.hr.manage.service.api.archive.IEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/employee", produces = "application/json",consumes = "application/json")
public class EmployeeController {
    @Autowired
    IEmployee iEmployee;

    @PostMapping("/add")
    public ResponseEntity addEmployee(@Valid EmployeeParam employeeParam) {
        return ResponseEntity.ok().body(iEmployee.addEmployee(employeeParam));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteEmployeeById(@Valid DeleteParams deleteParams) {
        return ResponseEntity.ok().body(iEmployee.deleteEmployeeById(deleteParams.getIds()));
    }

    @PutMapping("/update")
    public ResponseEntity updateEmployee(@Valid EmployeeParam employeeParam) {
        return ResponseEntity.ok().body(iEmployee.updateEmployee(employeeParam));
    }

    @GetMapping("/details")
    public ResponseEntity getEmployeeDetails(@Valid DeleteParam deleteParam) {
        return ResponseEntity.ok().body(iEmployee.getEmployeeDetails(deleteParam.getId()));
    }

    @GetMapping("/get_employees")
    public ResponseEntity getEmployeeList(@Valid InformationParam information,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iEmployee.getInformation(information));
    }
}
