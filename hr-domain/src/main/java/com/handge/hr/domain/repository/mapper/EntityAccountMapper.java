package com.handge.hr.domain.repository.mapper;

import com.handge.hr.domain.repository.pojo.EntityAccount;
import com.handge.hr.domain.repository.pojo.EntityEmployee;
import com.handge.hr.domain.repository.pojo.EntityRole;
import com.handge.hr.domain.repository.pojo.PrivilegePermission;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author Liujuhao
 * @date 2018/8/8.
 */
public interface EntityAccountMapper extends CommonMapper<EntityAccount, String> {

    @Select("SELECT * FROM entity_account WHERE username = #{username}")
    @Results({
            @Result(property = "username", column = "username"),
            @Result(property = "employee", column = "employee_id", one = @One(select = "findEmployeeById", fetchType = FetchType.LAZY)),
            @Result(property = "departmentName", column = "employee_id", one = @One(select = "findDeptNameByEmployeeId", fetchType = FetchType.LAZY)),
            @Result(property = "roleList", column = "id", many = @Many(select = "findRolesByAccountId", fetchType = FetchType.LAZY))
    })
    EntityAccount findByUsername(String username);

    @Select("SELECT " +
            "* " +
            "FROM entity_employee WHERE id = #{id}")
    EntityEmployee findEmployeeById(String id);

    @Select("SELECT " +
            "dept.name " +
            "FROM " +
            "entity_department dept " +
            "INNER JOIN entity_employee emp ON dept.id = emp.department_id " +
            "WHERE " +
            "emp.id = #{id}")
    String findDeptNameByEmployeeId(String id);

    @Select("SELECT " +
            "role.* " +
            "FROM " +
            "entity_role role, entity_m_role_to_account m WHERE m.role_id = role.id AND m.account_id = #{id}")
    @Results({
            @Result(property = "permissionList", column = "id", many = @Many(select = "com.handge.hr.domain.repository.mapper.EntityRoleMapper.getPermissionsByRoleId", fetchType = FetchType.LAZY))
    })
    List<EntityRole> findRolesByAccountId(String id);

    @Select("SELECT " +
            "DISTINCT(perm.*) " +
            "FROM " +
            "entity_account acc, " +
            "entity_m_role_to_account M, " +
            "entity_role role, " +
            "privilege_permission perm, " +
            "privilege_role_to_permission pm " +
            "WHERE " +
            "acc.username = #{username}  " +
            "AND acc.id = M.account_id  " +
            "AND M.role_id = role.id " +
            "AND perm.id = pm.permission_id  " +
            "AND pm.role_id = role.id ")
   List<PrivilegePermission> findPermissionByUsername(String username);
}