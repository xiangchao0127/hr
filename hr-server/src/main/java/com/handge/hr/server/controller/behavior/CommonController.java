package com.handge.hr.server.controller.behavior;

import com.handge.hr.behavior.service.api.common.ICommon;
import com.handge.hr.domain.entity.behavior.web.request.common.ChangePasswordFormParam;
import com.handge.hr.domain.entity.behavior.web.request.common.IpsByNumberParam;
import com.handge.hr.domain.entity.behavior.web.request.common.LoginFormParam;
import com.handge.hr.domain.entity.behavior.web.request.common.UserInfoByNameParam;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * Created by DaLu Guo on 2018/5/30.
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping(value = "/common", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
public class CommonController {

    @Autowired
    ICommon common;

    /**
     * 根据姓名匹配员工信息
     *
     * @param name 姓名
     * @return
     */
    @GetMapping("/user_info_by_name")
    public ResponseEntity listUserInfoByName(@Valid UserInfoByNameParam userInfoByNameParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(common.listUserInfoByName(userInfoByNameParam));
    }

    /**
     * 根据number获取IPS
     *
     * @param number
     * @return
     */
    @GetMapping("/ips_by_number")
    public ResponseEntity getIpsByNumber(@Valid IpsByNumberParam ipsByNumberParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                throw new UnifiedException(fieldError.getDefaultMessage(), ExceptionWrapperEnum.IllegalArgumentException);
            }
        }
        return ResponseEntity.ok().body(common.getIpsByNumber(ipsByNumberParam));
    }

}
