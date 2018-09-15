package com.handge.hr.server.controller.manage;

import com.handge.hr.domain.entity.base.web.request.PageParam;
import com.handge.hr.domain.entity.manage.web.request.workflow.*;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import com.handge.hr.manage.service.api.workflow.IProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/project", produces = "application/json")
public class ProjectController {

    @Autowired
    IProject project;

    /**
     * 创建项目
     */
    @PostMapping("/add_project")
    public ResponseEntity addProject(@Valid @RequestBody ArrayList<AddProjectParam> addProjectParamList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(project.addProject(addProjectParamList));
    }

    /**
     * 删除项目
     */
    @DeleteMapping("/delete_project")
    public ResponseEntity deleteProject(@Valid @RequestBody DeleteProjectParam deleteProjectParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(project.deleteProject(deleteProjectParam));
    }

    /**
     * 修改项目
     */
    @PutMapping("/update_project")
    public ResponseEntity modifyProject(@Valid @RequestBody AddProjectParam modifyProjectParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body((project.modifyProject(modifyProjectParam)));
    }

    /**
     * 接收项目
     */
    @PutMapping("/receive_project")
    public ResponseEntity receivedProject(@Valid @RequestBody List<ReceivedProjectParam> receivedProjectParamList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(project.receivedProject(receivedProjectParamList));
    }

    /**
     * 终止项目
     */
    @PutMapping("/terminate_project")
    public ResponseEntity terminatedProject(@Valid @RequestBody List<TerminatedProjectParam> terminatedProjectParamList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(project.terminatedProject(terminatedProjectParamList));
    }

    /**
     * QC分配
     */
    @PutMapping("/qc_distribution_project")
    public ResponseEntity projectQcDistribution(@Valid @RequestBody List<QcDistributionParam> qcDistributionParamList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(project.projectQcDistribution(qcDistributionParamList));
    }

    /**
     * 评价项目
     */
    @PutMapping("/evaluate_project")
    public ResponseEntity evaluateProject(@Valid @RequestBody ProjectEvaluateParam projectEvaluateParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(project.evaluateProject(projectEvaluateParam));
    }

    /**
     * 项目列表
     */
    @GetMapping("/get_project_list")
    public ResponseEntity getProjectList(@Valid ProjectSelectParam projectSelectParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(project.getProjectList(projectSelectParam));
    }

    /**
     * 项目总览(甘特图)
     */
    @GetMapping("/get_project_gantt_chart")
    public ResponseEntity getProjectsGanttChart(@Valid PageParam pageParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(project.getProjectsGanttChart(pageParam));
    }

    /**
     * 项目详情
     */
    @GetMapping("/get_project_details")
    public ResponseEntity getProjectDetails(@Valid ProjectDetailsParam projectDetailsParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(project.getProjectDetails(projectDetailsParam));
    }

    /**
     * 获取项目历史记录
     */
    @GetMapping("/get_project_history")
    public ResponseEntity getProjectHistory(@Valid ProjectHistorySelectParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(project.getProjectHistory(param));
    }

}
