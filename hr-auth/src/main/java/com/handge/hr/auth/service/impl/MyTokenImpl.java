package com.handge.hr.auth.service.impl;

import com.handge.hr.auth.service.api.IMyToken;
import com.handge.hr.domain.repository.mapper.EntityAccountMapper;
import com.handge.hr.domain.repository.pojo.EntityAccount;
import com.handge.hr.domain.repository.pojo.PrivilegePermission;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author Liujuhao
 * @date 2018/8/22.
 */
@Component
public class MyTokenImpl implements IMyToken {

    @Autowired
    EntityAccountMapper entityAccountMapper;

    @Override
    public EntityAccount getUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        EntityAccount token = (EntityAccount) subject.getPrincipals().getPrimaryPrincipal();
        EntityAccount user = entityAccountMapper.findByUsername(token.getUsername());
        return user;
    }

    @Override
    public List<PrivilegePermission> getPermissions() {
        Subject subject = SecurityUtils.getSubject();
        EntityAccount user = (EntityAccount) subject.getPrincipals().getPrimaryPrincipal();
        List<PrivilegePermission> permissions = entityAccountMapper.findPermissionByUsername(user.getUsername());
        return permissions;
    }
}
