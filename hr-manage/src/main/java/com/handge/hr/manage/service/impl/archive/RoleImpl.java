package com.handge.hr.manage.service.impl.archive;

import com.handge.hr.manage.common.utils.UuidUtils;
import com.handge.hr.manage.service.api.archive.IRole;
import com.handge.hr.domain.entity.manage.web.request.archive.AddRoleParam;
import com.handge.hr.domain.entity.manage.web.request.archive.DeleteRoleParam;
import com.handge.hr.domain.entity.manage.web.request.archive.FindRoleParam;
import com.handge.hr.domain.entity.manage.web.request.archive.ModifyRoleParam;
import com.handge.hr.domain.repository.mapper.EntityRoleMapper;
import com.handge.hr.domain.repository.pojo.EntityRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liuqian
 * @date 2018/8/2
 * @Description: 角色实现类
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class RoleImpl implements IRole {
    @Autowired
    EntityRoleMapper entityRoleMapper;

    @Override
    public int addRole(AddRoleParam addRoleParam) {
        EntityRole entityRole = new EntityRole();
        //id
        entityRole.setId(UuidUtils.getUUid());
        //角色名称
        entityRole.setName(addRoleParam.getName());
        //创建时间
        entityRole.setGmtCreate(new Date());
        //角色描述
        entityRole.setDescription(addRoleParam.getDescription());
        //上级角色id
        String id = entityRoleMapper.getIdByRoleName(addRoleParam.getParentRoleName());
        entityRole.setParentId(id);
        //角色性质
        entityRole.setRank(addRoleParam.getRank());
        entityRoleMapper.insert(entityRole);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRole(DeleteRoleParam deleteRoleParam) {
        List<String> ids = deleteRoleParam.getIds();
        List<EntityRole> list = new ArrayList<>();
        //判断要删除的角色是否存在下级角色，如果存在则不能删除
        ids.forEach(o -> {
            EntityRole entityJobPosition = entityRoleMapper.getRoleByParentRoleId(o);
            if (entityJobPosition != null) {
                list.add(entityJobPosition);
            }
        });
        //如果要删除的角色存在下级角色,list不为空
        if (list.isEmpty()) {
            entityRoleMapper.deleteByIdList(ids);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<EntityRole> findRole(FindRoleParam findRoleParam) {
        return entityRoleMapper.selectRoles(findRoleParam);
    }

    @Override
    public int modifyRole(ModifyRoleParam modifyRoleParam) {
        EntityRole entityRole = entityRoleMapper.getRoleById(modifyRoleParam.getId());
        //角色名称
        entityRole.setName(modifyRoleParam.getName());
        //修改时间
        entityRole.setGmtModified(new Date());
        //角色描述
        entityRole.setDescription(modifyRoleParam.getDescription());
        //上级角色id
        String id = entityRoleMapper.getIdByRoleName(modifyRoleParam.getParentRoleName());
        entityRole.setParentId(id);
        //角色性质
        entityRole.setRank(modifyRoleParam.getRank());
        entityRoleMapper.updateByPrimaryKey(entityRole);
        return 1;
    }
}
