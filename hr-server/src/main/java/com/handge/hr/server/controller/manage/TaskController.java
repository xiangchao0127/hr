package com.handge.hr.server.controller.manage;

import com.handge.hr.domain.entity.manage.web.request.workflow.*;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import com.handge.hr.manage.service.api.workflow.ITask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * create by xc in 2018/08/30
 */
@RestController
@RequestMapping(value = "/task", produces = "application/json")
public class TaskController {

    @Autowired
    ITask iTask;

    /**
     * 分配任务
     */
    @PostMapping("/add_task")
    public ResponseEntity addTask(@Valid List<AddTaskParam> addTaskParamList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iTask.addTask(addTaskParamList));
    }
    /**
     * 修改任务
     */
    @PutMapping("/update_task")
    public ResponseEntity motifyTask(@Valid AddTaskParam addTaskParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iTask.motifyTask(addTaskParam));
    }

    /**
     * 接收任务
     */
    @PutMapping("/receive_task")
    public ResponseEntity receiveTask(@Valid List<ReceiveTaskParam> receiveTaskParamList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iTask.receiveTask(receiveTaskParamList));
    }

    /**
     * QC分配
     */
    @PutMapping("/qc_distribution")
    public ResponseEntity qcDistribution(@Valid List<QcDistributionParam> qcDistributionParamList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iTask.taskQcDistribution(qcDistributionParamList));
    }

    /**
     * 提交任务
     */
    @PutMapping("/submit_task")
    public ResponseEntity submitTask(@Valid List<SubmitTaskParam> submitTaskParamList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iTask.submitTask(submitTaskParamList));
    }

    /**
     * 删除任务
     */
    @DeleteMapping("/delete_task")
    public ResponseEntity deleteTask(@Valid DeleteTaskParam deleteTaskParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iTask.deleteTask(deleteTaskParam));
    }

    /**
     * 终止任务
     */
    @PutMapping("/terminated_task")
    public ResponseEntity terminatedTask(@Valid List<TerminatedTaskParam> terminatedTaskParamList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iTask.terminatedTask(terminatedTaskParamList));
    }

    /**
     * QC评价
     */
    @PutMapping("/qc_evaluate")
    public ResponseEntity qcEvaluate(@Valid QcEvaluateParam qcEvaluateParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iTask.qcEvaluate(qcEvaluateParam));
    }

    /**
     * 任务评价
     */
    @PutMapping("/evaluate_task")
    public ResponseEntity taskEvaluate(@Valid TaskEvaluateParam taskEvaluateParm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iTask.taskEvaluate(taskEvaluateParm));
    }

    /**
     * 获取任务列表
     *
     * @return
     */
    @GetMapping("/get_task_list")
    public ResponseEntity getTaskList(@Valid TaskSelectParam taskSelectParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iTask.getTaskList(taskSelectParam));
    }

    /**
     * 任务详情
     */
    @GetMapping("/task_details")
    public ResponseEntity getTaskDetails(@Valid TaskDetailsParam taskDetailsParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iTask.getTaskDetails(taskDetailsParam));
    }

    /**
     * 获取任务历史记录
     */
    @GetMapping("/get_task_history")
    public ResponseEntity getTaskHistory(@Valid TaskHistorySelectParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iTask.getTaskHistory(param));
    }

}
