package com.handge.hr.auth.service.impl;

import com.handge.hr.auth.service.api.IIdentification;
import com.handge.hr.auth.shiro.ShiroEncryptor;
import com.handge.hr.common.enumeration.behavior.RolePropertyEnum;
import com.handge.hr.domain.entity.behavior.web.request.common.ChangePasswordFormParam;
import com.handge.hr.domain.entity.behavior.web.request.common.LoginFormParam;
import com.handge.hr.domain.entity.behavior.web.response.common.LoginRes;
import com.handge.hr.domain.entity.behavior.web.response.common.UserInfo;
import com.handge.hr.domain.repository.mapper.EntityAccountMapper;
import com.handge.hr.domain.repository.pojo.EntityAccount;
import com.handge.hr.domain.repository.pojo.EntityRole;
import com.handge.hr.exception.custom.UnifiedException;
import com.handge.hr.exception.enumeration.ExceptionWrapperEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liujuhao
 * @date 2018/6/27.
 */

@Component
public class IdentificationImpl implements IIdentification {

    @Autowired
    EntityAccountMapper entityAccountMapper;

    String successDescription;

    @Value("${custom.login.hyper-role.ip-validate}")
    boolean isValidateHyperRoleIP;

    @Value("${custom.login.hyper-role.allows}")
    List<String> allowIPs;

    @Value("${custom.login.role.is-validate}")
    boolean isValidateRole;

    @Override
    public Object login(LoginFormParam loginFormParam) {
        LoginRes loginRes = new LoginRes();
        String username = loginFormParam.getUsername();
        String password = loginFormParam.getPassword();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, ShiroEncryptor.securityTransform(password, username));
        subject.login(token);
        loginRes.setToken(subject.getSession().getId().toString());
        loginRes.setDescription(successDescription);
        UserInfo userInfo = new UserInfo();
        loginRes.setUserInfo(userInfo);
        EntityAccount user = (EntityAccount) subject.getPrincipals().getPrimaryPrincipal();
        userInfo.setDepartment(user.getDepartmentName());
        userInfo.setEmployeeName(user.getEmployee().getName());
        userInfo.setNumber(user.getEmployee().getJobNumber());
        List<String> rolePropertyList = user.getRoleList().stream().map(EntityRole::getRank).collect(Collectors.toList());
        Collections.sort(rolePropertyList, String::compareTo);
        userInfo.setRoleList(rolePropertyList);
        if (isValidateRole) {
            if (!isAccess(rolePropertyList)) {
                throw new UnifiedException(userInfo.getEmployeeName(), ExceptionWrapperEnum.Auth_Role_Grade_Error);
            }
        }
        if (isValidateHyperRoleIP) {
            if (!isValidHyperViosr(rolePropertyList, subject.getSession().getHost())) {
                throw new UnifiedException(subject.getSession().getHost(), ExceptionWrapperEnum.Auth_IP_VALID_HYPER_ERROR);
            }
        }
        return loginRes;
    }

    /**
     * 判断用户角色是否可以进入系统
     *
     * @return
     */
    private boolean isAccess(List<String> rolePropertyList) {
        return !(rolePropertyList.isEmpty() || (rolePropertyList.size() == 1 && rolePropertyList.contains(RolePropertyEnum.Default.getCode())));
    }

    /**
     * 判断IP是否允许超级管理员登录
     *
     * @param rolePropertyList
     * @param host
     * @return
     */
    private boolean isValidHyperViosr(List<String> rolePropertyList, String host) {
        return !rolePropertyList.contains(RolePropertyEnum.HyperVisor.getCode()) || allowIPs.contains(host);
    }

    /**
     * 认证失败或认证过期
     *
     * @return
     */
    @Override
    public Object unauth() {
        throw new UnifiedException("",ExceptionWrapperEnum.Auth_TOKEN_ERROR);
//        ModelAndView modelAndView = new ModelAndView();
//        MappingJackson2JsonView view = new MappingJackson2JsonView();
//        Map<String, Object> attributes = new HashMap();
//        attributes.put("code", ExceptionWrapperEnum.Auth_TOKEN_ERROR.getCode());
//        attributes.put("description", ExceptionWrapperEnum.Auth_TOKEN_ERROR.getExplain2());
//        view.setAttributesMap(attributes);
//        modelAndView.setView(view);
//        return modelAndView;
    }

    @Override
    public Object logout() {
        Subject subject = SecurityUtils.getSubject();
        UserInfo userInfo = new UserInfo();
        EntityAccount user = (EntityAccount) subject.getPrincipals().getPrimaryPrincipal();
        EntityAccount authAccount = entityAccountMapper.findByUsername(user.getUsername());
        userInfo.setDepartment(authAccount.getDepartmentName());
        userInfo.setEmployeeName(authAccount.getEmployee().getName());
        userInfo.setNumber(authAccount.getEmployee().getJobNumber());
        userInfo.setRoleList(authAccount.getRoleList().stream().map(EntityRole::getRank).collect(Collectors.toList()));
        LoginRes loginRes = new LoginRes();
        loginRes.setDescription(successDescription);
        loginRes.setUserInfo(userInfo);
        subject.logout();
        return loginRes;
    }

    @Override
    public Object changePassword(ChangePasswordFormParam changePasswordFormParam) {
        String username = changePasswordFormParam.getUsername();
        String oldPwd = changePasswordFormParam.getOldPassword();
        String newPwd = changePasswordFormParam.getNewPassword();

        Subject subject = SecurityUtils.getSubject();
        EntityAccount user = (EntityAccount) subject.getPrincipals().getPrimaryPrincipal();
        if (user.getUsername().equals(username)) {
            if (user.getPassword().equals(ShiroEncryptor.securityTransform(oldPwd, username))) {
                user.setGmtModified(new Date());
                user.setPassword(ShiroEncryptor.securityTransform(newPwd, username));
                entityAccountMapper.updateByPrimaryKey(user);
            } else {
                throw new UnifiedException("原密码", ExceptionWrapperEnum.Auth_Match_Error);
            }
        } else {
            throw new UnifiedException("用户名", ExceptionWrapperEnum.Auth_Match_Error);
        }
        return 1;
    }
}
