package com.handge.hr.domain.entity.manage.web.response.workflow;

/**
 * create by xc in 2018/08/21
 */
public class ProjectEventRes {
    /**
     * 修改前
      */
    private String  beforeModified;
    /**
     * 修改后
     */
    private String  afterModified;

    public String getBeforeModified() {
        return beforeModified;
    }

    public void setBeforeModified(String beforeModified) {
        this.beforeModified = beforeModified;
    }

    public String getAfterModified() {
        return afterModified;
    }

    public void setAfterModified(String afterModified) {
        this.afterModified = afterModified;
    }

    @Override
    public String toString() {
        return "ProjectEventRes{" +
                "beforeModified='" + beforeModified + '\'' +
                ", afterModified='" + afterModified + '\'' +
                '}';
    }
}
