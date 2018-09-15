package com.handge.hr.domain.entity.manage.web.request.archive;

public class DeleteParam {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DeleteParam{" +
                "id='" + id + '\'' +
                '}';
    }
}
