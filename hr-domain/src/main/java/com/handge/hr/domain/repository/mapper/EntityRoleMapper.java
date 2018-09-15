package com.handge.hr.domain.repository.mapper;

import com.handge.hr.common.utils.StringUtils;
import com.handge.hr.domain.entity.manage.web.request.archive.FindRoleParam;
import com.handge.hr.domain.repository.pojo.EntityRole;
import com.handge.hr.domain.repository.pojo.PrivilegePermission;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author liuqian
 * @date 2018/8/2
 * @Description: 角色
 */
@Mapper
public interface EntityRoleMapper extends CommonMapper<EntityRole,String> {
    /**
     * 通过角色名称查询角色id
     * @param name
     * @return
     */
    @Select("select id from entity_role where name = #{name}")
    String getIdByRoleName(String name);

    /**
     * 通过角色id查询角色信息
     * @param id
     * @return
     */
    @Select("select * from entity_role where id=#{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "permissionList", column = "id", many = @Many(select = "getPermissionsByRoleId", fetchType = FetchType.LAZY))
    })
    EntityRole getRoleById(String id);

    /**
     * 通过上级角色id查询角色信息
     * @param parentRoleId
     * @return
     */
    @Select("select * from entity_role where parent_id=#{parentRoleId}")
    EntityRole getRoleByParentRoleId(String parentRoleId);

    /**
     * 查询角色（模糊查询）
     * @param findRoleParam
     * @return
     */
    @SelectProvider(type = RoleSqlBuilder.class,method = "findRoles")
    List<EntityRole> selectRoles(FindRoleParam findRoleParam);
    class RoleSqlBuilder{
        public static String findRoles(FindRoleParam findRoleParam){
            return new SQL() {
                {
                    SELECT("e.id,e.name,e.gmt_create as createTime,e.gmt_modified as modifyTime,\n" +
                            "e.description ,e2.name as parentRoleName,e.rank");
                    FROM("entity_role e");
                    LEFT_OUTER_JOIN("entity_role e2 ON e.parent_id = e2.id");
                    WHERE("1=1");
                    if(StringUtils.notEmpty(findRoleParam.getName())){
                        AND();
                        WHERE("e.name like CONCAT('%',#{name},'%')");
                    }
                    if(StringUtils.notEmpty(findRoleParam.getDescription())){
                        AND();
                        WHERE("e.description like CONCAT('%',#{description},'%')");
                    }
                    if(StringUtils.notEmpty(findRoleParam.getParentRoleName())){
                        AND();
                        WHERE("e2.name like CONCAT('%',#{parentRoleName},'%')");
                    }
                    if(StringUtils.notEmpty(findRoleParam.getRank())){
                        AND();
                        WHERE("e.rank like CONCAT('%',#{rank},'%')");
                    }
                }
            }.toString();
        }
    }

    /**
     * 查询角色拥有的权限
     */
    @Select("SELECT perm.* FROM privilege_permission perm, privilege_role_to_permission m WHERE perm.id = m.permission_id AND m.role_id = #{roleId}")
    List<PrivilegePermission> getPermissionsByRoleId(String roleId);
}
