package com.handge.hr.server.controller.manage;

import com.handge.hr.domain.entity.manage.web.request.pointcard.AddPointCardParam;
import com.handge.hr.domain.entity.manage.web.request.pointcard.ListPointCardParam;
import com.handge.hr.domain.entity.manage.web.request.pointcard.UpdatePointCardParam;
import com.handge.hr.domain.entity.manage.web.request.pointcard.ViewPointCardParam;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import com.handge.hr.manage.service.api.workflow.IPointCard;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by DaLu Guo on 2018/8/29.
 */
@RestController
@RequestMapping(value = "/point_card", produces = "application/json",consumes = "application/json")
public class PointCardController {

    @Autowired
    IPointCard iPointCard;

//    /**
//     * 新增积分卡实例
//     * @param addPointCardParam
//     * @param bindingResult
//     * @return
//     */
//    @PostMapping("add_point_card")
//    public ResponseEntity addPointCard(@Valid AddPointCardParam addPointCardParam, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//            for (FieldError fieldError : fieldErrors) {
//                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
//            }
//        }
//        return ResponseEntity.ok().body(iPointCard.addPointCard(addPointCardParam));
//    }
    /**
     * 查看积分卡实例
     * @param viewPointCardParam
     * @param bindingResult
     * @return
     */
    @GetMapping("view_point_card")
    public ResponseEntity viewPointCard(@Valid ViewPointCardParam viewPointCardParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iPointCard.viewPointCard(viewPointCardParam));
    }

    /**
     * 更新积分卡实例
     * @param updatePointCardParam
     * @param bindingResult
     * @return
     */
    @PostMapping("update_point_card")
    public ResponseEntity updatePointCard(@Valid @RequestBody UpdatePointCardParam updatePointCardParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iPointCard.updatePointCard(updatePointCardParam));
    }

    /**
     * 积分卡列表
     * @param listPointCardParam
     * @param bindingResult
     * @return
     */
    @RequiresPermissions(value = "monitor:get:1")
    @GetMapping("/list_point_card")
    public ResponseEntity listPointCard(@Valid ListPointCardParam listPointCardParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getField(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(iPointCard.listPointCard(listPointCardParam));
    }
}
