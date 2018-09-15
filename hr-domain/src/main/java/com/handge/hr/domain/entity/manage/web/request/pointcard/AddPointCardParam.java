package com.handge.hr.domain.entity.manage.web.request.pointcard;

/**
 * Created by DaLu Guo on 2018/8/22.
 */
public class AddPointCardParam {
    /**
     * 积分卡内容
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
    /**
     * 所属积分卡ID
     */
    private String pointCardId;

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

    public String getPointCardId() {
        return pointCardId;
    }

    public void setPointCardId(String pointCardId) {
        this.pointCardId = pointCardId;
    }

    @Override
    public String toString() {
        return "AddPointCardParam{" +
                "content='" + content + '\'' +
                ", origin='" + origin + '\'' +
                ", originId='" + originId + '\'' +
                ", pointCardId='" + pointCardId + '\'' +
                '}';
    }
}
