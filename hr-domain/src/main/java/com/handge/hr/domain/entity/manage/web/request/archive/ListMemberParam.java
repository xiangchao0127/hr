package com.handge.hr.domain.entity.manage.web.request.archive;

/**
 * @author guodalu
 * @date 2018/8/1
 * @Description:
 */
public class ListMemberParam {
    /**
     * 部门id
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ListMemberParam{" +
                "id='" + id + '\'' +
                '}';
    }
}
