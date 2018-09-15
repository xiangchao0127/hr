package com.handge.hr.server.controller.manage;

import com.handge.hr.domain.entity.manage.web.request.archive.InformationParam;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import com.handge.hr.manage.service.api.archive.IExcelInformationExport;
import com.handge.hr.manage.service.api.archive.IExcelInformationImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/excel", produces = "application/json")
public class ExcelController {

    @Autowired
    IExcelInformationExport exportExcel;
    @Autowired
    IExcelInformationImport importExcel;

    @GetMapping("/export_information_by_template")
    public ResponseEntity exportInformation(@Valid InformationParam information,HttpServletResponse response,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(exportExcel.exportInformationByTemplate(information,response));
    }

    @GetMapping("/import_employee_by_information")
    public ResponseEntity importInformation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(importExcel.importInformation());
    }

    @GetMapping("/import_common_by_enum")
    public ResponseEntity importDictCommon(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(importExcel.importDictCommon());
    }

    @GetMapping("/import_attendance_flow")
    public ResponseEntity importAttendanceFlowRecords(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(importExcel.importAttendanceFlowRecords());
    }
}
