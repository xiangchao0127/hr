package com.handge.hr.domain.entity.manage.web.response.archive;

public class DictCommonData {
    private Object id;
    private String name;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DictCommonData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
