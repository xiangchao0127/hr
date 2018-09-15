package com.handge.hr.domain.entity.manage.web.request.workflow;


import java.util.List;

/**
 * Created by MaJianfu on 2018/8/16.
 */
public class DeleteProjectParam {
    /**
     * 项目id
     */
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
