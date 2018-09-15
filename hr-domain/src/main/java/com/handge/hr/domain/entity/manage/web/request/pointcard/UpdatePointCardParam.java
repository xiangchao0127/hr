package com.handge.hr.domain.entity.manage.web.request.pointcard;

/**
 * Created by DaLu Guo on 2018/8/23.
 */
public class UpdatePointCardParam {
    private String id;
    /**
     * 修改的结果
     */
    private Object content;
    /**
     * 来源类型（页面）
     */
    private String origin;
    /**
     * 来源实例(实例ID)
     */
    private String originId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    @Override
    public String toString() {
        return "UpdatePointCardParam{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", origin='" + origin + '\'' +
                ", originId='" + originId + '\'' +
                '}';
    }
}
