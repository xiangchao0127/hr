package com.handge.hr.domain.entity.manage.web.request.archive;

import java.util.List;

/**
 * @author guodalu
 * @date 2018/8/1
 * @Description:
 */
public class DeleteDepartmentParam {
    /**
     * 部门id集合
     */
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "DeleteDepartmentParam{" +
                "ids=" + ids +
                '}';
    }
}
