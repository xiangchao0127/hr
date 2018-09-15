package com.handge.hr.domain.entity.manage.web.request.pointcard;

/**
 * @author liuqian
 * @date 2018/8/23
 * @Description:查询积分卡
 */
public class ListPointCardParam {
    /**
     * 来源类型
     */
    String origin;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return "ListPointCardParam{" +
                "origin='" + origin + '\'' +
                '}';
    }
}
