package com.handge.hr.domain.repository.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name="entity_role")
public class EntityRole implements Serializable{

    @Id
    private String id;

    private String name;

    private Date gmtCreate;

    private Date gmtModified;

    private String description;

    private String parentId;

    private String rank;

    @Transient
    private List<PrivilegePermission> permissionList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank == null ? null : rank.trim();
    }

    public List<PrivilegePermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<PrivilegePermission> permissionList) {
        this.permissionList = permissionList;
    }

    @Override
    public String toString() {
        return "EntityRole{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", description='" + description + '\'' +
                ", parentId='" + parentId + '\'' +
                ", rank='" + rank + '\'' +
                ", permissionList=" + permissionList +
                '}';
    }
}