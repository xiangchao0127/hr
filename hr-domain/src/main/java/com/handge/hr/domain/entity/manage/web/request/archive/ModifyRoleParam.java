package com.handge.hr.domain.entity.manage.web.request.archive;

/**
 * @author liuqian
 * @date 2018/8/3
 * @Description: 修改角色
 */
public class ModifyRoleParam {
    /**
     * id
     */
    private String id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 上级角色名称
     */
    private String parentRoleName;
    /**
     * 角色性质
     */
    private String rank;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentRoleName() {
        return parentRoleName;
    }

    public void setParentRoleName(String parentRoleName) {
        this.parentRoleName = parentRoleName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "AddRoleParam{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parentRoleName='" + parentRoleName + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
