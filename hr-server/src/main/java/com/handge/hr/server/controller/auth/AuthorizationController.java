package com.handge.hr.server.controller.auth;

import com.handge.hr.auth.service.api.IIdentification;
import com.handge.hr.behavior.service.api.common.ICommon;
import com.handge.hr.domain.entity.behavior.web.request.common.ChangePasswordFormParam;
import com.handge.hr.domain.entity.behavior.web.request.common.LoginFormParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * Created by DaLu Guo on 2018/5/30.
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping(value = "/common", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
public class AuthorizationController {

    @Autowired
    IIdentification identification;

    /**
     * 用户登录
     *
     * @param userInfo
     * @return
     */
    @PostMapping(value = "/login")
    public ResponseEntity login(@Valid @RequestBody LoginFormParam loginFormParam, BindingResult bindingResult) {
        return ResponseEntity.ok().body(identification.login(loginFormParam));
    }

    /**
     * 未登录跳转响应
     *
     * @return
     */
    @RequestMapping(value = "/unauth")
    public ResponseEntity unauth() {
        return ResponseEntity.ok().body(identification.unauth());
    }

    /**
     * 用户登出
     *
     * @return
     */
    @PostMapping(value = "/logout")
    public ResponseEntity logout() {
        return ResponseEntity.ok().body(identification.logout());
    }

    /**
     * 修改密码
     *
     * @return
     */
    @PostMapping(value = "/change_password")
    public ResponseEntity changePassword(@Valid @RequestBody ChangePasswordFormParam changePasswordFormParam, BindingResult bindingResult) {
        return ResponseEntity.ok().body(identification.changePassword(changePasswordFormParam));
    }

}
