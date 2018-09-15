package com.handge.hr.domain.entity.manage.web.request.workflow;


import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created by MaJianfu on 2018/8/16.
 */
public class DeleteTaskParam {
    /**
     * 任务id
     */
    @NotEmpty
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
