package com.handge.hr.domain.entity.manage.web.request.pointcard;

/**
 * Created by DaLu Guo on 2018/8/29.
 */
public class ViewPointCardParam {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ViewPointCardParam{" +
                "id='" + id + '\'' +
                '}';
    }
}
