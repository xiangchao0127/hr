package com.handge.hr.auth.service.api;

import com.handge.hr.domain.repository.pojo.EntityAccount;
import com.handge.hr.domain.repository.pojo.PrivilegePermission;

import java.util.List;

/**
 * @author Liujuhao
 * @date 2018/8/22.
 */
public interface IMyToken {

    /**
     * 获取用户信息
     * @return
     */
    EntityAccount getUserInfo();

    /**
     * 获取用户的所有权限
     * @return
     */
    List<PrivilegePermission> getPermissions();
}
