package com.handge.hr.domain.entity.manage.web.request.archive;

import java.util.List;

/**
 * @author liuqian
 * @date 2018/8/2
 * @Description: 删除角色
 */
public class DeleteRoleParam {
    /**
     * id集合
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
        return "DeleteRoleParam{" +
                "ids=" + ids +
                '}';
    }
}
