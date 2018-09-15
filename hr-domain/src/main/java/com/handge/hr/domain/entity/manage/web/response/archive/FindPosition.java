package com.handge.hr.domain.entity.manage.web.response.archive;

import java.util.Date;

/**
 * @author liuqian
 * @date 2018/8/1
 * @Description: 查询岗位（模糊查询）
 */
public class FindPosition {
    /**
     * id
     */
    private String id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date modifyTime;
    /**
     * 岗位名称
     */
    private String name;
    /**
     * 岗位描述
     */
    private String description;
    /**
     * 状态
     */
    private String status;
    /**
     * 上级岗位
     */
    private String higherPositionName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHigherPositionName() {
        return higherPositionName;
    }

    public void setHigherPositionName(String higherPositionName) {
        this.higherPositionName = higherPositionName;
    }

    @Override
    public String toString() {
        return "FindPosition{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", higherPositionName='" + higherPositionName + '\'' +
                '}';
    }
}
