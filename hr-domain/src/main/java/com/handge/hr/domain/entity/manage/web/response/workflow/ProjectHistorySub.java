package com.handge.hr.domain.entity.manage.web.response.workflow;

/**
 * create by xc in 2018/09/06
 */
public class ProjectHistorySub {
    /**
     * 时间
     */
    private String date;
    /**
     * 事件内容
     */
    private String content;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ProjectHistorySub{" +
                "date='" + date + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
