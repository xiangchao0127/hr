package com.handge.hr.manage.service.api.archive;


import com.handge.hr.domain.entity.manage.web.request.archive.AddRoleParam;
import com.handge.hr.domain.entity.manage.web.request.archive.DeleteRoleParam;
import com.handge.hr.domain.entity.manage.web.request.archive.FindRoleParam;
import com.handge.hr.domain.entity.manage.web.request.archive.ModifyRoleParam;
import com.handge.hr.domain.repository.pojo.EntityRole;

import java.util.List;

/**
 * @author liuqian
 * @date 2018/8/2
 * @Description: 角色CRUD接口
 */
public interface IRole {
    /**
     * 添加角色
     * @param addRoleParam
     * @return
     */
    int addRole(AddRoleParam addRoleParam);

    /**
     * 删除角色
     * @param deleteRoleParam
     * @return
     */
    int deleteRole(DeleteRoleParam deleteRoleParam);

    /**
     * 查询角色（模糊查询）
     * @param findRoleParam
     * @return
     */
    List<EntityRole> findRole(FindRoleParam findRoleParam);

    /**
     * 修改角色
     * @param modifyRoleParam
     * @return
     */
    int modifyRole(ModifyRoleParam modifyRoleParam);
}
