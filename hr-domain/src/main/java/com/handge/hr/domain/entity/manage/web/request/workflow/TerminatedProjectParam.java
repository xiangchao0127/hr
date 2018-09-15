package com.handge.hr.domain.entity.manage.web.request.workflow;


/**
 * Created by MaJianfu on 2018/8/16.
 */
public class TerminatedProjectParam {
    /**
     * 项目id
     */
    private String id;
    /**
     * 终止原因
     */
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
