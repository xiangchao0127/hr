package com.handge.hr.domain.entity.manage.web.request.archive;

/**
 * @author liuqian
 * @date 2018/8/1
 * @Description: 查询岗位
 */
public class FindPositionParam {
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
     * 上级岗位名称
     */
    private String higherPositionName;

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
        return "FindPositionParam{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", higherPositionName='" + higherPositionName + '\'' +
                '}';
    }
}
